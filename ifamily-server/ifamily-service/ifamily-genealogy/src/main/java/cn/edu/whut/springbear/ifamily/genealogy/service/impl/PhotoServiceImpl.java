package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.constant.RegExpConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.PhotoVO;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PhotoMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PhotoBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PhotoDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PhotoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 09:32
 */
@RequiredArgsConstructor
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, PhotoDO> implements PhotoService {

    private final PeopleService peopleService;

    @Override
    public boolean create(Long genealogyId, Long uploaderUserId, List<String> imgUrls) {
        if (CollUtil.isEmpty(imgUrls)) {
            throw new IncorrectConditionException("相册图片列表不能为空");
        }

        List<PhotoDO> photoDOList = new ArrayList<>();
        Date date = new Date();
        for (String imgUrl : imgUrls) {
            if (ReUtil.isMatch(RegExpConstants.URL_PATTERN, imgUrl)) {
                PhotoDO photoDO = new PhotoDO();
                photoDO.setGenealogyId(genealogyId);
                photoDO.setUploaderUserId(uploaderUserId);
                photoDO.setUrl(imgUrl);
                photoDO.setCreated(date);
                photoDO.setModified(date);
                photoDO.setDeleted(AssertEnum.NO.getCode());
                photoDOList.add(photoDO);
            }
        }

        // 批量保存家族图片列表
        return this.saveBatch(photoDOList);
    }

    @Override
    public List<PhotoBO> page(PageQuery pageQuery, Long genealogyId) {
        /*
         * 查询家族相册分页数据，根据创建时间去重降序排列后进行分页，而后过滤出所有的创建时间
         * SQL: select distinct created
         *      from genealogy_photo
         *      where genealogy_id = #{genealogy_id}
         *      order by created desc
         *      limit #{current},#{size}
         */
        QueryWrapper<PhotoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct `created`").eq("genealogy_id", genealogyId).orderByDesc("created");
        Page<PhotoDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }
        List<Date> dateList = pageData.getRecords().stream().map(PhotoDO::getCreated).collect(Collectors.toList());

        List<PhotoBO> resultList = new ArrayList<>();
        // 遍历所有的创建日期，依次查询每个创建日期下的所有图片记录，按照上传者用户 ID 进行分组
        for (Date created : dateList) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("genealogy_id", genealogyId).eq("created", created);
            List<PhotoDO> datePhotos = this.list(queryWrapper);
            Map<Long, List<PhotoDO>> userPhotoMap = datePhotos.stream().collect(Collectors.groupingBy(PhotoDO::getUploaderUserId));
            // 遍历当前创建日期下的所有上传用户，查询其家族人员信息，而后将结果封装为 PhotoBO
            for (Map.Entry<Long, List<PhotoDO>> userPhoto : userPhotoMap.entrySet()) {
                PeopleVO userPeople = this.peopleService.getUserPeople(userPhoto.getKey(), genealogyId);
                if (userPeople != null) {
                    PhotoBO photoBO = new PhotoBO();
                    // 上传者
                    PeopleCardVO uploader = new PeopleCardVO();
                    BeanUtils.copyProperties(userPeople, uploader);
                    photoBO.setUploader(uploader);
                    // 上传时间
                    photoBO.setUploadTime(created);
                    // 图片集合
                    List<PhotoVO> imgUrls = new ArrayList<>();
                    List<PhotoDO> images = userPhoto.getValue();
                    for (PhotoDO image : images) {
                        PhotoVO imageVO = new PhotoVO();
                        imageVO.setId(image.getId());
                        imageVO.setUrl(image.getUrl());
                        imgUrls.add(imageVO);
                    }
                    photoBO.setImgUrls(imgUrls);
                    resultList.add(photoBO);
                }
            }
        }

        return resultList;
    }

}
