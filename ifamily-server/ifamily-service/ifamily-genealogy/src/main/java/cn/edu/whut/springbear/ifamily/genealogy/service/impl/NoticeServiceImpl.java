package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.constant.GenealogyMessageConstants;
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
        noticeDO.setApproval(AssertEnum.NO.getCode());
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
        // 查询家族已审核公告，按发布时间逆序排列
        QueryWrapper<NoticeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).eq("is_approval", AssertEnum.YES.getCode()).orderByDesc("created");
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

    @Override
    public boolean edit(Long id, String content, Long genealogyId) {
        NoticeDO noticeDO = this.getByGenealogyId(id, genealogyId);
        if (noticeDO == null) {
            throw new IncorrectConditionException(GenealogyMessageConstants.NOTICE_NOT_EXISTS);
        }

        // 编辑家族公告
        NoticeDO updateDO = new NoticeDO();
        updateDO.setId(id);
        updateDO.setContent(content);
        updateDO.setApproval(AssertEnum.NO.getCode());
        return this.updateById(updateDO);
    }

    @Override
    public boolean remove(Long id, Long genealogyId) {
        NoticeDO noticeDO = this.getByGenealogyId(id, genealogyId);
        if (noticeDO == null) {
            throw new IncorrectConditionException(GenealogyMessageConstants.NOTICE_NOT_EXISTS);
        }

        return this.removeById(id);
    }

    /**
     * 通过公告 ID 和家族 ID 获取家族公告
     *
     * @param noticeId    公告 ID
     * @param genealogyId 家族 ID
     * @return 家族公告
     */
    private NoticeDO getByGenealogyId(Long noticeId, Long genealogyId) {
        QueryWrapper<NoticeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", noticeId).eq("genealogy_id", genealogyId);
        return this.getOne(queryWrapper);
    }

}
