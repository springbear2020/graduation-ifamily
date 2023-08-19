package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.NoticeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.NoticeDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/15 20:50
 */
public interface NoticeService extends IService<NoticeDO> {

    /**
     * 保存家族公告
     *
     * @param genealogyId     家族 ID
     * @param announcerUserId 发布者用户 ID
     * @param content         公告内容
     * @return [true]保存成功
     */
    boolean create(Long genealogyId, Long announcerUserId, String content);

    /**
     * 查询家族公告分页数据
     *
     * @param pageQuery   分页条件
     * @param genealogyId 家族 ID
     * @return 家族公告数据
     */
    List<NoticeBO> page(PageQuery pageQuery, Long genealogyId);

}
