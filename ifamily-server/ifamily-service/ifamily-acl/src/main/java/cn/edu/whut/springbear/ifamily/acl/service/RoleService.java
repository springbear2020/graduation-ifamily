package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 根据角色状态和角色 ID 集合查询角色列表
     *
     * @param roleIds 角色 ID 集合
     * @param status  角色禁用状态：[0]启用 [1]禁用
     * @return 角色列表
     */
    List<RoleDO> listByStatusInBatchIds(List<Long> roleIds, Integer status);

}
