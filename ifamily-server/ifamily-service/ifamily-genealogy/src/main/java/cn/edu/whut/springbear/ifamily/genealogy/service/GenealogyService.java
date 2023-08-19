package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:57
 */
public interface GenealogyService extends IService<GenealogyDO> {

    /**
     * 创建家族
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean create(GenealogyQuery genealogyQuery, Long creatorUserId);

    /**
     * 查询用户关联的所有家族详细信息，包含家族信息、成员概况信息、家族创建者和管理员信息
     */
    List<GenealogyDetailsBO> listWithDetails(Long userId);

    /**
     * 更新家族信息
     */
    boolean edit(GenealogyQuery genealogyQuery);

}
