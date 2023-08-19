package cn.edu.whut.springbear.ifamily.social.service;

import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentDetailsBO;
import cn.edu.whut.springbear.ifamily.social.pojo.po.MomentDO;
import cn.edu.whut.springbear.ifamily.social.pojo.query.MomentQuery;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentBO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 18:02
 */
public interface MomentService extends IService<MomentDO> {

    /**
     * 发布用户动态
     *
     * @param userId      用户 ID
     * @param momentQuery 动态内容
     * @return [true]发布成功
     */
    boolean create(Long userId, MomentQuery momentQuery);

    /**
     * 查询用户动态分页数据
     *
     * @param userId    用户 ID
     * @param pageQuery 分页条件
     * @return 用户动态数据
     */
    List<MomentDetailsBO> page(Long userId, PageQuery pageQuery);

    /**
     * 删除用户动态
     *
     * @param userId   用户 ID
     * @param momentId 动态 ID
     * @return [true]删除成功
     */
    boolean remove(Long userId, Long momentId);

    /**
     * 查询用户动态
     *
     * @param userId   用户 ID
     * @param momentId 动态 ID
     * @return 用户动态数据
     */
    MomentBO get(Long userId, Long momentId);

    /**
     * 更新用户动态
     *
     * @param userId      用户 ID
     * @param momentQuery 新动态内容
     * @return [true]更新成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean edit(Long userId, MomentQuery momentQuery);

    /**
     * 查询家族成员动态分页数据
     *
     * @param currentUserId 当前登录用户 ID
     * @param genealogyId   家族 ID
     * @param pageQuery     分页条件
     * @return 家族成员动态分页数据
     */
    List<MomentDetailsBO> pageByGenealogyId(Long currentUserId, Long genealogyId, PageQuery pageQuery);

}
