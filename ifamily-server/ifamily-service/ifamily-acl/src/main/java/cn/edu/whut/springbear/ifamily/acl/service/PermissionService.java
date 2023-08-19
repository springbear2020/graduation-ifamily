package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.PermissionQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface PermissionService extends IService<PermissionDO> {

    /**
     * 根据权限 ID 和权限禁用状态查询权限
     *
     * @param permissionId 权限 ID
     * @param status       权限禁用状态：[0]启用 [1]禁用
     * @return 权限
     */
    PermissionDO getByStatusAndId(Long permissionId, Integer status);

    /**
     * 保存系统权限资源
     *
     * @param permissionQuery 权限数据
     * @return [true]保存成功
     */
    boolean create(PermissionQuery permissionQuery);

    /**
     * 更新系统权限资源
     *
     * @param permissionQuery 新权限数据
     * @return [true]更新成功
     */
    boolean edit(PermissionQuery permissionQuery);

}
