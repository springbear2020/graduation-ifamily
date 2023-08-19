package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyDetailsBO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:57
 */
public interface GenealogyService extends IService<GenealogyDO> {

    /**
     * 创建家族
     */
    boolean create(GenealogyQuery genealogyQuery, Long userId);

    /**
     * 查询用户关联的所有家族，包含家族成员概况信息、家族创建者和管理员等信息
     */
    List<GenealogyDetailsBO> listWithDetailsOfUser(Long userId);

    /**
     * 更新家族信息
     */
    boolean edit(GenealogyQuery genealogyQuery);

}
