package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.AdminRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface AdminRoleService extends IService<AdminRoleDO> {

    /**
     * 查询管理员拥有的所有角色名称集合
     *
     * @param adminId 管理员 ID
     * @return 角色名称集合
     */
    List<String> listRoleNamesOfAdmin(Long adminId);

}
