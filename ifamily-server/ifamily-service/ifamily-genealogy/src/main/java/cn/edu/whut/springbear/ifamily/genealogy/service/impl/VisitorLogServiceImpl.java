package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.VisitorLogMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.VisitorBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.VisitorLogDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.VisitorLogService;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/07 16:15
 */
@AllArgsConstructor
@Service
public class VisitorLogServiceImpl extends ServiceImpl<VisitorLogMapper, VisitorLogDO> implements VisitorLogService {

    private final MemberService memberService;

    @Override
    public boolean create(Long userId, Long genealogyId) {
        VisitorLogDO visitorLogDO = new VisitorLogDO();
        visitorLogDO.setVisitorUserId(userId);
        visitorLogDO.setGenealogyId(genealogyId);
        Date date = new Date();
        visitorLogDO.setVisitedDate(date);
        visitorLogDO.setCreated(date);
        visitorLogDO.setModified(date);
        visitorLogDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(visitorLogDO);
    }

    @Override
    public List<VisitorBO> page(PageQuery pageQuery, Long genealogyId) {
        /*
         * 查询家族访问记录分页数据，根据访问日期去重降序排列后进行分页，而后过滤出家族下的所有访问日期
         * SQL: select distinct visited_date
         *      from visitor_log
         *      where genealogy_id = #{genealogy_id}
         *      order by visited_date desc
         *      limit #{current},#{size}
         */
        QueryWrapper<VisitorLogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct `visited_date`").eq("genealogy_id", genealogyId).orderByDesc("visited_date");
        Page<VisitorLogDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }
        List<Date> dateList = pageData.getRecords().stream().map(VisitorLogDO::getVisitedDate).collect(Collectors.toList());

        // 遍历每个日期，查询每个访问日期下的访问人员用户 ID 列表，而后根据用户 ID 列表和家族 ID 查询家族人员信息
        List<VisitorBO> resultList = new ArrayList<>();
        for (Date date : dateList) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("genealogy_id", genealogyId).eq("visited_date", date);
            List<VisitorLogDO> dateLogs = this.list(queryWrapper);
            List<Long> visitorUserIds = dateLogs.stream().map(VisitorLogDO::getVisitorUserId).distinct().collect(Collectors.toList());
            // 根据用户 ID 和家族 ID 批量查询家族人员信息
            List<PeopleDO> peopleDOList = this.memberService.listMemberInBatchUserIds(visitorUserIds, genealogyId);
            List<PeopleCardVO> visitorsOnDate = new ArrayList<>();
            for (PeopleDO peopleDO : peopleDOList) {
                // DO -> VO
                PeopleCardVO peopleCardVO = new PeopleCardVO();
                BeanUtils.copyProperties(peopleDO, peopleCardVO);
                visitorsOnDate.add(peopleCardVO);
            }
            // 将访问日期和每个日期下的访问人员列表封装为 BO，并添加至结果列表
            VisitorBO visitorBO = new VisitorBO();
            visitorBO.setVisitedDate(date);
            visitorBO.setVisitors(visitorsOnDate);
            resultList.add(visitorBO);
        }

        return resultList;
    }

}
