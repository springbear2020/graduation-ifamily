package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PhotoBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PhotoDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 09:31
 */
public interface PhotoService extends IService<PhotoDO> {

    /**
     * 保存家族相册图片
     *
     * @param genealogyId    家族 ID
     * @param uploaderUserId 上传者用户 ID
     * @param imgUrls        图片列表
     * @return [true]保存成功
     */
    boolean create(Long genealogyId, Long uploaderUserId, List<String> imgUrls);

    /**
     * 查询家族相册分页数据
     *
     * @param pageQuery   分页条件
     * @param genealogyId 家族 ID
     * @return 家族相册列表数据
     */
    List<PhotoBO> page(PageQuery pageQuery, Long genealogyId);

}
