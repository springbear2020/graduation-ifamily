package cn.edu.whut.springbear.ifamily.social.service;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.PhotoVO;
import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentPhotoDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 18:03
 */
public interface MomentPhotoService extends IService<MomentPhotoDO> {

    /**
     * 保存用户动态图片
     *
     * @param userId   用户 ID
     * @param momentId 动态 ID
     * @param imgUrls  动态图片列表
     * @return [true]保存成功
     */
    boolean save(Long userId, Long momentId, List<String> imgUrls);

    /**
     * 查询动态下的所有图片
     *
     * @param momentId 动态 ID
     * @return 图片列表
     */
    List<PhotoVO> listByMomentId(Long momentId);

    /**
     * 更新用户动态图片列表：首先删除已存在的所有记录，而后插入新记录
     *
     * @param userId   用户 ID
     * @param momentId 动态 ID
     * @param imgUrls  动态新图片列表
     * @return [true]更新成功
     */
    boolean updateUserMomentPhotos(Long userId, Long momentId, List<String> imgUrls);

}
