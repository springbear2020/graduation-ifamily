package cn.edu.whut.springbear.ifamily.social.service;

import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentCommentDO;
import cn.edu.whut.springbear.ifamily.social.pojo.query.MomentCommentQuery;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentCommentBO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 11:46
 */
public interface MomentCommentService extends IService<MomentCommentDO> {

    /**
     * 用户评论动态
     *
     * @param userId             用户 ID
     * @param momentCommentQuery 评论内容
     * @return 保存成功返回记录 ID，否则返回 null
     */
    Long create(Long userId, MomentCommentQuery momentCommentQuery);

    /**
     * 查询动态评论
     *
     * @param momentId 动态 ID
     * @return 动态评论列表
     */
    List<MomentCommentBO> listByMomentId(Long momentId);

}
