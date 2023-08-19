package cn.edu.whut.springbear.ifamily.backend.service;

import cn.edu.whut.springbear.ifamily.backend.pojo.bo.AdminBO;
import cn.edu.whut.springbear.ifamily.backend.pojo.po.AdminDO;
import cn.edu.whut.springbear.ifamily.backend.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageParam;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.AdminUserVO;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.RoleUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:32
 */
public interface AdminService extends IService<AdminDO> {

    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return 管理员基本信息
     */
    UserDTO loadAdminByUsername(String username);

    /**
     * 管理员登录
     *
     * @param loginQuery 用户名和密码
     * @return 登录成功返回签发的 token
     */
    CommonResult<Object> login(LoginQuery loginQuery);

    /**
     * 从请求头获取当前登录管理员信息，包含管理员对应的角色集合
     *
     * @return 管理员信息
     */
    AdminBO current();

    /**
     * 批量查询管理员信息
     *
     * @param adminIds 管理员 ID 集合
     * @return 管理员信息集合
     */
    List<RoleUserVO> listInBatchIds(List<Long> adminIds);

    /**
     * 查询管理员用户分页数据
     *
     * @param pageQuery 分页条件
     * @return 管理员用户分页数据
     */
    List<AdminUserVO> pageData(PageParam pageQuery);

    /**
     * 更新管理员账号禁用状态
     *
     * @param adminId   管理员 ID
     * @param newStatus 新禁用状态：[0]启用 [1]禁用
     * @return 更新成功
     */
    boolean updateStatus(Long adminId, Integer newStatus);

}
