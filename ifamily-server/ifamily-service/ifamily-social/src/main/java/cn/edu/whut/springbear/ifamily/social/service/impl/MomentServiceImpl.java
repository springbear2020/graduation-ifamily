package cn.edu.whut.springbear.ifamily.social.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.PhotoVO;
import cn.edu.whut.springbear.ifamily.social.mapper.MomentMapper;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentDetailsBO;
import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentDO;
import cn.edu.whut.springbear.ifamily.social.pojo.query.MomentQuery;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentCommentBO;
import cn.edu.whut.springbear.ifamily.social.pojo.vo.MomentLikeUserVO;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentBO;
import cn.edu.whut.springbear.ifamily.social.service.MomentCommentService;
import cn.edu.whut.springbear.ifamily.social.service.MomentLikeService;
import cn.edu.whut.springbear.ifamily.social.service.MomentPhotoService;
import cn.edu.whut.springbear.ifamily.social.service.MomentService;
import cn.edu.whut.springbear.ifamily.social.service.feign.GenealogyFeignService;
import cn.edu.whut.springbear.ifamily.social.service.feign.UserFeignService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 18:02
 */
@RequiredArgsConstructor
@Service
public class MomentServiceImpl extends ServiceImpl<MomentMapper, MomentDO> implements MomentService {

    private final MomentPhotoService momentPhotoService;
    private final UserFeignService userFeignService;
    private final GenealogyFeignService genealogyFeignService;
    private final MomentLikeService momentLikeService;
    private final MomentCommentService momentCommentService;

    @Override
    public boolean create(Long userId, MomentQuery momentQuery) {
        MomentDO momentDO = new MomentDO();
        BeanUtils.copyProperties(momentQuery, momentDO);
        momentDO.setPostUserId(userId);
        Date date = new Date();
        momentDO.setCreated(date);
        momentDO.setModified(date);
        momentDO.setDeleted(AssertEnum.NO.getCode());
        // 设置可查看当前动态的人员家族 ID
        Long genealogyId = this.genealogyFeignService.getUserDefaultGenealogyId(userId);
        momentDO.setGenealogyId(genealogyId);
        boolean save = this.save(momentDO);

        boolean saveImgUrls = true;
        if (CollUtil.isNotEmpty(momentQuery.getImgUrls())) {
            // 保存动态相册图片
            saveImgUrls = this.momentPhotoService.save(userId, momentDO.getId(), momentQuery.getImgUrls());
        }

        return save && saveImgUrls;
    }

    @Override
    public List<MomentDetailsBO> page(Long userId, PageQuery pageQuery) {
        QueryWrapper<MomentDO> queryWrapper = new QueryWrapper<>();
        String todayStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        queryWrapper.eq("post_user_id", userId).lt("scheduled", todayStr).orderByDesc("scheduled");
        Page<MomentDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }

        return this.getMomentsDetails(userId, pageData.getRecords());
    }

    @Override
    public boolean remove(Long userId, Long momentId) {
        UpdateWrapper<MomentDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_user_id", userId).eq("id", momentId);
        return this.remove(updateWrapper);
    }

    @Override
    public MomentBO get(Long userId, Long momentId) {
        QueryWrapper<MomentDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_user_id", userId).eq("id", momentId);
        MomentDO momentDO = this.getOne(queryWrapper);
        if (momentDO == null) {
            return null;
        }

        // DO -> BO
        MomentBO momentBO = new MomentBO();
        BeanUtils.copyProperties(momentDO, momentBO);
        List<PhotoVO> momentPhotoDOList = this.momentPhotoService.listByMomentId(momentDO.getId());
        momentBO.setImgUrls(momentPhotoDOList);

        return momentBO;
    }

    @Override
    public boolean edit(Long userId, MomentQuery momentQuery) {
        // 更新用户动态图片列表
        boolean updatePhotos = this.momentPhotoService.updateUserMomentPhotos(userId, momentQuery.getId(), momentQuery.getImgUrls());
        // 更新用户动态信息
        MomentDO momentDO = new MomentDO();
        BeanUtils.copyProperties(momentQuery, momentDO);
        UpdateWrapper<MomentDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", momentDO.getId()).eq("post_user_id", userId);
        return updatePhotos && this.update(momentDO, updateWrapper);
    }

    @Override
    public List<MomentDetailsBO> pageByGenealogyId(Long currentUserId, Long genealogyId, PageQuery pageQuery) {
        QueryWrapper<MomentDO> queryWrapper = new QueryWrapper<>();
        String todayStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        queryWrapper.eq("genealogy_id", genealogyId).lt("scheduled", todayStr).orderByDesc("scheduled");
        Page<MomentDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }

        return this.getMomentsDetails(currentUserId, pageData.getRecords());
    }

    /**
     * 获取动态详细信息
     *
     * @param currentUserId 当前登录用户 ID
     * @param momentList    动态列表
     * @return 动态详细信息
     */
    private List<MomentDetailsBO> getMomentsDetails(Long currentUserId, List<MomentDO> momentList) {
        // MomentDOList -> momentDetailsBOList
        List<MomentDetailsBO> momentDetailsBOList = new ArrayList<>();
        for (MomentDO momentDO : momentList) {
            MomentDetailsBO momentDetailsBO = new MomentDetailsBO();
            BeanUtils.copyProperties(momentDO, momentDetailsBO);

            // 当前动态的发布用户信息
            CommonUserVO publisher = this.userFeignService.getById(momentDO.getPostUserId());
            momentDetailsBO.setPublisher(publisher);

            // 查询当前动态的所有图片列表
            List<PhotoVO> momentPhotoDOList = this.momentPhotoService.listByMomentId(momentDO.getId());
            momentDetailsBO.setImgUrls(momentPhotoDOList);

            // 查询当前动态的点赞列表
            List<MomentLikeUserVO> likeList = this.momentLikeService.listByMomentId(momentDO.getId());
            momentDetailsBO.setLikeList(likeList);

            // 查询当前动态的评论列表
            List<MomentCommentBO> commentList = this.momentCommentService.listByMomentId(momentDO.getId());
            momentDetailsBO.setCommentList(commentList);

            // 判断当前登录用户是否已点赞过当前动态
            boolean currentUserLiked = false;
            if (CollUtil.isNotEmpty(likeList)) {
                currentUserLiked = likeList.stream().anyMatch(item -> currentUserId.equals(item.getUserId()));
            }
            momentDetailsBO.setCurrentUserLiked(currentUserLiked);

            momentDetailsBOList.add(momentDetailsBO);
        }

        return momentDetailsBOList;
    }

}
