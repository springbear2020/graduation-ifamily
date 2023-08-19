package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.RevisionLogMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.RevisionBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.RevisionLogDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.RevisionLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/07 11:50
 */
@Service
public class RevisionLogServiceImpl extends ServiceImpl<RevisionLogMapper, RevisionLogDO> implements RevisionLogService {

    /**
     * 使用 @Lazy 注解将 Bean 的实例化延迟到第一次使用时，从而避免 PeopleServiceImpl <-> RevisionLogServiceImpl 循环依赖
     */
    @Lazy
    @Resource
    private PeopleService peopleService;

    @Override
    public boolean create(Integer type, String peopleName, Long peopleId, Long genealogyId, Long operatorUserId) {
        RevisionLogDO revisionLogDO = new RevisionLogDO();
        revisionLogDO.setOperationType(type);
        revisionLogDO.setOperatedPeopleName(peopleName);
        revisionLogDO.setOperatedPeopleId(peopleId);
        revisionLogDO.setGenealogyId(genealogyId);
        revisionLogDO.setOperatorUserId(operatorUserId);
        Date date = new Date();
        revisionLogDO.setOperationDate(date);
        revisionLogDO.setCreated(date);
        revisionLogDO.setModified(date);
        revisionLogDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(revisionLogDO);
    }

    @Override
    public List<RevisionBO> page(PageQuery pageQuery, Long genealogyId) {
        /*
         * 查询家族修谱日志分页数据，根据修谱日期去重降序排列后进行分页，而后过滤出家族下的所有修谱日期
         * SQL: select distinct operation_date
         *      from genealogy_revision_log
         *      where genealogy_id = #{genealogy_id}
         *      order by operation_date desc
         *      limit #{current},#{size}
         */
        QueryWrapper<RevisionLogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct `operation_date`").eq("genealogy_id", genealogyId).orderByDesc("operation_date");
        Page<RevisionLogDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }
        List<Date> dateList = pageData.getRecords().stream().map(RevisionLogDO::getOperationDate).collect(Collectors.toList());

        List<RevisionBO> resultList = new ArrayList<>();
        // 遍历每一个修谱日期，依次查询每个日期下的修谱日志，最后将结果封装为 RevisionBO 对象
        for (Date date : dateList) {
            // 查询当前日期下的所有修谱日志
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("genealogy_id", genealogyId).eq("operation_date", date);
            List<RevisionLogDO> dateRevisionLogs = this.list(queryWrapper);
            // 将当前日期下的所有修谱记录按照操作用户 ID 进行分组
            Map<Long, List<RevisionLogDO>> operatorUserMap = dateRevisionLogs.stream().collect(Collectors.groupingBy(RevisionLogDO::getOperatorUserId));

            // 遍历查询每个操作用户的家族人员信息和其操作的人员列表信息，封装为 RevisionBO.OperationLog 对象
            List<RevisionBO.OperationLog> dateLogs = new ArrayList<>();
            for (Map.Entry<Long, List<RevisionLogDO>> operatorEntry : operatorUserMap.entrySet()) {
                // 查询操作人员信息
                Long operatorUserId = operatorEntry.getKey();
                PeopleVO peopleVO = this.peopleService.getUserPeople(operatorUserId, genealogyId);
                PeopleCardVO operatorVO = new PeopleCardVO();
                BeanUtils.copyProperties(peopleVO, operatorVO);

                // 将所有被操作人员按照操作类型【增、删、改、查】进行分组，而后查询每组下的人员列表信息
                Map<Integer, List<RevisionLogDO>> typeMap = operatorEntry.getValue().stream().collect(Collectors.groupingBy(RevisionLogDO::getOperationType));
                for (Map.Entry<Integer, List<RevisionLogDO>> typeEntry : typeMap.entrySet()) {
                    RevisionBO.OperationLog log = new RevisionBO.OperationLog();
                    // 操作者
                    log.setOperator(operatorVO);
                    // 操作类型
                    Integer operationType = typeEntry.getKey();
                    log.setOperationType(operationType);
                    // 被操作人员列表
                    List<String> operatedPeopleNames = typeEntry.getValue().stream().map(RevisionLogDO::getOperatedPeopleName).collect(Collectors.toList());
                    log.setOperatedPeopleList(operatedPeopleNames);
                    dateLogs.add(log);
                }
            }

            RevisionBO revisionBO = new RevisionBO();
            revisionBO.setOperationDate(date);
            revisionBO.setDateLogs(dateLogs);
            resultList.add(revisionBO);
        }

        return resultList;
    }

}