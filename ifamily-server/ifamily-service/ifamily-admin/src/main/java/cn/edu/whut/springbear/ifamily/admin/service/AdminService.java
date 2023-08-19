package cn.edu.whut.springbear.ifamily.admin.service;

import cn.edu.whut.springbear.ifamily.admin.pojo.po.AdminDO;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:32
 */
public interface AdminService extends IService<AdminDO> {

    /**
     * 根据用户名查询管理员
     */
    UserDTO loadAdminByUsername(String username);

}
