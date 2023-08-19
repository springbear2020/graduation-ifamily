package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:34
 */
public interface UserGenealogyService extends IService<UserGenealogyDO> {

    /**
     * 将家族与用户关联，并设置为用户的默认家族，且将用户设置为家族管理员
     */
    boolean createAndAssociate(Long userId, Long genealogyId);

    /**
     * 查询用户关联的所有家族
     */
    List<UserGenealogyDO> listGenealogiesOfUser(Long userId);

    /**
     * 获取家族管理员用户 ID 列表
     */
    List<Long> listAdminIdsOfGenealogy(Long genealogyId);

    /**
     * 将指定家族设置为用户默认家族
     */
    boolean setDefault(Long userId, Long genealogyId);

    /**
     * 查询用户默认家族
     */
    UserGenealogyDO getDefault(Long userId);

}
