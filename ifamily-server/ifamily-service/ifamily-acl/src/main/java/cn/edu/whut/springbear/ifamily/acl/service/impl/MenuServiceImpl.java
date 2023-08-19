package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.MenuMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.MenuDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.MenuQuery;
import cn.edu.whut.springbear.ifamily.acl.service.AdminRoleService;
import cn.edu.whut.springbear.ifamily.acl.service.MenuService;
import cn.edu.whut.springbear.ifamily.acl.service.RoleMenuService;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@RequiredArgsConstructor
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {

    private final AdminRoleService adminRoleService;
    private final RoleMenuService roleMenuService;

    @Override
    public boolean create(MenuQuery menuQuery) {
        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(menuQuery, menuDO);
        menuDO.setId(null);
        Date date = new Date();
        menuDO.setModified(date);
        menuDO.setCreated(date);
        return this.save(menuDO);
    }

    @Override
    public boolean edit(MenuQuery menuQuery) {
        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(menuQuery, menuDO);
        return this.updateById(menuDO);
    }

    @Override
    public List<String> listMenuPathsOfAdmin(Long adminId) {
        // 查询管理员对应的所有角色 ID
        List<Long> adminRoleIds = this.adminRoleService.listRoleIdsOfAdmin(adminId);
        if (CollUtil.isEmpty(adminRoleIds)) {
            return null;
        }

        // 查询角色列表对应的所有菜单 ID
        Set<Long> rolesMenuIds = this.roleMenuService.listMenuIdsInRoleIds(adminRoleIds);
        if (CollUtil.isEmpty(rolesMenuIds)) {
            return null;
        }

        // 批量查询未禁用菜单列表集合
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", AssertEnum.NO.getCode()).in("id", rolesMenuIds);
        List<MenuDO> adminMenus = this.list(queryWrapper);
        if (CollUtil.isEmpty(adminMenus)) {
            return null;
        }

        return adminMenus.stream()
                .map(MenuDO::getPath)
                .collect(Collectors.toList());
    }

}
