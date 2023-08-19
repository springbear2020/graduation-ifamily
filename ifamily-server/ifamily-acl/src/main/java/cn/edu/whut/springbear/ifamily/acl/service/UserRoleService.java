package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.model.po.UserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface UserRoleService extends IService<UserRoleDO> {

    /**
     * 查询用户对应的角色 ID 集合，一个用户对应多个角色
     *
     * @param userId 用户 ID
     * @return 角色 ID 列表
     */
    List<Long> listRoleIdsOfUser(Long userId);

}
