package cn.edu.whut.springbear.ifamily.social.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import cn.edu.whut.springbear.ifamily.social.mapper.MomentLikeMapper;
import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentLikeDO;
import cn.edu.whut.springbear.ifamily.social.pojo.vo.MomentLikeUserVO;
import cn.edu.whut.springbear.ifamily.social.service.MomentLikeService;
import cn.edu.whut.springbear.ifamily.social.service.feign.UserFeignService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 19:55
 */
@RequiredArgsConstructor
@Service
public class MomentLikeServiceImpl extends ServiceImpl<MomentLikeMapper, MomentLikeDO> implements MomentLikeService {

    private final UserFeignService userFeignService;

    @Override
    public Long create(Long userId, Long momentId) {
        CommonUserVO commonUserVO = this.userFeignService.getById(userId);
        if (commonUserVO == null) {
            return null;
        }

        MomentLikeDO momentLikeDO = new MomentLikeDO();
        momentLikeDO.setUsername(commonUserVO.getUsername());
        momentLikeDO.setAvatar(commonUserVO.getAvatar());
        momentLikeDO.setNickname(commonUserVO.getNickname());
        momentLikeDO.setUserId(userId);
        momentLikeDO.setMomentId(momentId);
        Date date = new Date();
        momentLikeDO.setCreated(date);
        momentLikeDO.setModified(date);
        momentLikeDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(momentLikeDO) ? momentLikeDO.getId() : null;
    }

    @Override
    public List<MomentLikeUserVO> listByMomentId(Long momentId) {
        QueryWrapper<MomentLikeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("moment_id", momentId);
        List<MomentLikeDO> momentLikeDOList = this.list(queryWrapper);
        if (CollUtil.isEmpty(momentLikeDOList)) {
            return null;
        }

        // DOList -> VOList
        List<MomentLikeUserVO> resultList = new ArrayList<>();
        for (MomentLikeDO momentLikeDO : momentLikeDOList) {
            MomentLikeUserVO momentLikeUserVO = new MomentLikeUserVO();
            BeanUtils.copyProperties(momentLikeDO, momentLikeUserVO);
            resultList.add(momentLikeUserVO);
        }
        return resultList;
    }

    @Override
    public boolean remove(Long userId, Long momentId) {
        UpdateWrapper<MomentLikeDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).eq("moment_id", momentId);
        return this.remove(updateWrapper);
    }

}
