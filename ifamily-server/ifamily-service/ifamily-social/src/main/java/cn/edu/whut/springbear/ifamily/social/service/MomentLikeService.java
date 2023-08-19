package cn.edu.whut.springbear.ifamily.social.service;

import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentLikeDO;
import cn.edu.whut.springbear.ifamily.social.pojo.vo.MomentLikeUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 19:55
 */
public interface MomentLikeService extends IService<MomentLikeDO> {

    /**
     * 用户点赞动态
     *
     * @param userId   用户 ID
     * @param momentId 动态 ID
     * @return 保存成功返回记录 ID，否则返回 null
     */
    Long create(Long userId, Long momentId);

    /**
     * 查询所有点赞当前动态的用户
     *
     * @param momentId 动态 ID
     * @return 用户列表
     */
    List<MomentLikeUserVO> listByMomentId(Long momentId);

    /**
     * 用户取消点赞动态
     *
     * @param userId   用户 ID
     * @param momentId 动态 ID
     * @return [true]删除成功
     */
    boolean remove(Long userId, Long momentId);

}
