package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.UserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface UserRoleService extends IService<UserRoleDO> {

    /**
     * 查询用户拥有的所有角色名称集合
     *
     * @param userId 用户 ID
     * @return 角色名称集合
     */
    List<String> listRoleNamesOfUser(Long userId);

}
