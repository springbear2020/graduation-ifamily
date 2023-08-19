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
     * 更新用户所关联的所有家族的默认家族状态
     *
     * @param userId 用户 ID
     * @param status 家族的新默认状态
     * @return 更新成功
     */
    boolean updateDefault(Long userId, Integer status);

    /**
     * 新增用户家族，并将新增的家族设置为用户默认家族
     */
    boolean createDefault(Long userId, Long genealogyId);

    /**
     * 查询用户关联的所有家族
     */
    List<UserGenealogyDO> listGenealogiesOfUser(Long userId);

    /**
     * 更新用户默认家族
     */
    boolean setDefault(Long userId, Long genealogyId);

    /**
     * 查询用户默认家族
     */
    UserGenealogyDO getDefault(Long userId);

    /**
     * 获取家族管理员用户 ID 列表
     */
    List<Long> listAdminIdsOfGenealogy(Long genealogyId);

}
