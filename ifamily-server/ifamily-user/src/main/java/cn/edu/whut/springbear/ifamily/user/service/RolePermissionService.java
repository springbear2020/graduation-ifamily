package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.RolePermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface RolePermissionService extends IService<RolePermissionDO> {

    /**
     * 查询用户角色列表下对应的权限 ID 集合，一个角色对应多个权限
     *
     * @param roleIdList 角色 ID 集合
     * @return 权限列表
     */
    List<Long> listPermissionIdsOfRoles(List<Long> roleIdList);

}
