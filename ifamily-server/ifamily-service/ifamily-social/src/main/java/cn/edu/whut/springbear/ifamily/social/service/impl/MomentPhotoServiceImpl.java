package cn.edu.whut.springbear.ifamily.social.service.impl;

import cn.edu.whut.springbear.ifamily.common.constant.RegExpConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.PhotoVO;
import cn.edu.whut.springbear.ifamily.social.mapper.MomentPhotoMapper;
import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentPhotoDO;
import cn.edu.whut.springbear.ifamily.social.service.MomentPhotoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 18:03
 */
@Service
public class MomentPhotoServiceImpl extends ServiceImpl<MomentPhotoMapper, MomentPhotoDO> implements MomentPhotoService {

    @Override
    public boolean save(Long userId, Long momentId, List<String> imgUrls) {
        if (CollUtil.isEmpty(imgUrls)) {
            return true;
        }

        Date date = new Date();
        List<MomentPhotoDO> entityList = new ArrayList<>();
        for (String imgUrl : imgUrls) {
            if (ReUtil.isMatch(RegExpConstants.URL_PATTERN, imgUrl)) {
                MomentPhotoDO momentPhotoDO = new MomentPhotoDO();
                momentPhotoDO.setUrl(imgUrl);
                momentPhotoDO.setMomentId(momentId);
                momentPhotoDO.setUploaderUserId(userId);
                momentPhotoDO.setCreated(date);
                momentPhotoDO.setModified(date);
                momentPhotoDO.setDeleted(AssertEnum.NO.getCode());
                entityList.add(momentPhotoDO);
            }
        }

        return this.saveBatch(entityList);
    }

    @Override
    public List<PhotoVO> listByMomentId(Long momentId) {
        QueryWrapper<MomentPhotoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("moment_id", momentId);
        List<MomentPhotoDO> momentPhotoDOList = this.list(queryWrapper);
        if (CollUtil.isEmpty(momentPhotoDOList)) {
            return null;
        }

        // DOList -> VOList
        List<PhotoVO> resultList = new ArrayList<>();
        for (MomentPhotoDO momentPhotoDO : momentPhotoDOList) {
            PhotoVO momentPhotoVO = new PhotoVO();
            BeanUtils.copyProperties(momentPhotoDO, momentPhotoVO);
            resultList.add(momentPhotoVO);
        }

        return resultList;
    }

    @Override
    public boolean updateUserMomentPhotos(Long userId, Long momentId, List<String> imgUrls) {
        // 删除用户动态已存在的所有图片列表
        UpdateWrapper<MomentPhotoDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uploader_user_id", userId).eq("moment_id", momentId);
        this.remove(updateWrapper);

        if (CollUtil.isEmpty(imgUrls)) {
            return true;
        }

        // 插入用户动态新图片列表
        Date date = new Date();
        List<MomentPhotoDO> entityList = new ArrayList<>();
        for (String imgUrl : imgUrls) {
            MomentPhotoDO momentPhotoDO = new MomentPhotoDO();
            momentPhotoDO.setUploaderUserId(userId);
            momentPhotoDO.setMomentId(momentId);
            momentPhotoDO.setUrl(imgUrl);
            momentPhotoDO.setCreated(date);
            momentPhotoDO.setModified(date);
            momentPhotoDO.setDeleted(AssertEnum.NO.getCode());
            entityList.add(momentPhotoDO);
        }

        return this.saveBatch(entityList);
    }

}
