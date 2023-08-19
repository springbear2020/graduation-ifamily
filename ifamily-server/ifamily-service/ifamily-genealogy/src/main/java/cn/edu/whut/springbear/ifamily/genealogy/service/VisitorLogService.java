package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.VisitorBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.VisitorLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/07 16:15
 */
public interface VisitorLogService extends IService<VisitorLogDO> {

    /**
     * 保存家族访问记录
     *
     * @param userId      访问者用户 ID
     * @param genealogyId 被访问家族 ID
     * @return [true]保存成功
     */
    boolean create(Long userId, Long genealogyId);

    /**
     * 查询家族访问记录分页数据
     *
     * @param pageQuery   分页查询条件
     * @param genealogyId 家族 ID
     * @return 家族访问记录分页数据
     */
    List<VisitorBO> page(PageQuery pageQuery, Long genealogyId);

}
