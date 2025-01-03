package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.NoticeMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.NoticeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.NoticeDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.NoticeService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/15 20:50
 */
@AllArgsConstructor
@Repository
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeDO> implements NoticeService {

    private final PeopleService peopleService;

    @Override
    public boolean create(Long genealogyId, Long announcerUserId, String content) {
        NoticeDO noticeDO = new NoticeDO();
        noticeDO.setContent(content);
        noticeDO.setGenealogyId(genealogyId);
        noticeDO.setAnnouncerUserId(announcerUserId);
        Date date = new Date();
        noticeDO.setCreated(date);
        noticeDO.setModified(date);
        noticeDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(noticeDO);
    }

    @Override
    public List<NoticeBO> page(PageQuery pageQuery, Long genealogyId) {
        // 查询家族公告，按发布时间逆序排列
        QueryWrapper<NoticeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).orderByDesc("created");
        Page<NoticeDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }

        List<NoticeBO> resultList = new ArrayList<>();
        for (NoticeDO noticeDO : pageData.getRecords()) {
            // 遍历每一条公告，查询发布者家族人员信息
            PeopleVO announcerVO = this.peopleService.getUserPeople(noticeDO.getAnnouncerUserId(), genealogyId);
            if (announcerVO != null) {
                PeopleCardVO announcerCardVO = new PeopleCardVO();
                BeanUtils.copyProperties(announcerVO, announcerCardVO);
                // 封装公告内容，并添加至结果列表
                NoticeBO noticeBO = new NoticeBO();
                noticeBO.setAnnouncer(announcerCardVO);
                BeanUtils.copyProperties(noticeDO, noticeBO);
                resultList.add(noticeBO);
            }
        }

        return resultList;
    }

}
