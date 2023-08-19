package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface PermissionService extends IService<PermissionDO> {

    /**
     * 查询用户拥有的所有权限
     */
    List<PermissionDO> listPermissionsOfUser(Long userId);

    /**
     * 查询系统所有权限
     */
    List<PermissionDO> list();

}
