package cn.edu.whut.springbear.ifamily.social.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.social.mapper.MomentCommentMapper;
import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentCommentDO;
import cn.edu.whut.springbear.ifamily.social.pojo.query.MomentCommentQuery;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentCommentBO;
import cn.edu.whut.springbear.ifamily.social.service.MomentCommentService;
import cn.edu.whut.springbear.ifamily.social.service.feign.UserFeignService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 11:46
 */
@RequiredArgsConstructor
@Service
public class MomentCommentServiceImpl extends ServiceImpl<MomentCommentMapper, MomentCommentDO> implements MomentCommentService {

    private final UserFeignService userFeignService;

    @Override
    public Long create(Long userId, MomentCommentQuery momentCommentQuery) {
        MomentCommentDO momentCommentDO = new MomentCommentDO();
        BeanUtils.copyProperties(momentCommentQuery, momentCommentDO);
        momentCommentDO.setSourceUserId(userId);
        Date date = new Date();
        momentCommentDO.setCreated(date);
        momentCommentDO.setModified(date);
        momentCommentDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(momentCommentDO) ? momentCommentDO.getId() : null;
    }

    @Override
    public List<MomentCommentBO> listByMomentId(Long momentId) {
        QueryWrapper<MomentCommentDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("moment_id", momentId);
        List<MomentCommentDO> commentDOList = this.list(queryWrapper);
        if (CollUtil.isEmpty(commentDOList)) {
            return null;
        }

        // DOList -> VOList
        List<MomentCommentBO> resultList = new ArrayList<>();
        for (MomentCommentDO momentCommentDO : commentDOList) {
            MomentCommentBO momentCommentBO = new MomentCommentBO();
            BeanUtils.copyProperties(momentCommentDO, momentCommentBO);

            // 查询评论发布者用户信息
            momentCommentBO.setSourceUser(this.userFeignService.getById(momentCommentDO.getSourceUserId()));
            // 查询被回复者用户信息
            momentCommentBO.setTargetUser(this.userFeignService.getById(momentCommentDO.getTargetUserId()));
            resultList.add(momentCommentBO);
        }

        return resultList;
    }

}
