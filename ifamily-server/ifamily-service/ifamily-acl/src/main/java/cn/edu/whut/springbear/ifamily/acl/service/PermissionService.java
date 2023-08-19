package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
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

}
