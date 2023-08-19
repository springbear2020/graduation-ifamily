package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.MenuDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.MenuQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface MenuService extends IService<MenuDO> {

    /**
     * 保存前台路由菜单资源
     *
     * @param menuQuery 菜单信息
     * @return [true]保存成功
     */
    boolean create(MenuQuery menuQuery);

    /**
     * 更新前台路由菜单资源
     *
     * @param menuQuery 新菜单信息
     * @return [true]更新成功
     */
    boolean edit(MenuQuery menuQuery);

    /**
     * 查询管理员对应的菜单路径集合
     *
     * @param adminId 管理员 ID
     * @return 菜单路径集合
     */
    List<String> listMenuPathsOfAdmin(Long adminId);

}
