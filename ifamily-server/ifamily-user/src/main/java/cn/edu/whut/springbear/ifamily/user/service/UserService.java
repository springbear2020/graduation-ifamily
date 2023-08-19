package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息，将查询到的用户信息 UserDO 封装为 cn.edu.whut.springbear.ifamily.user.pojo.dto.SecurityUserDetailsDTO，
     * 以供 SpringSecurity 安全框架鉴权使用
     *
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 用户登录：根据（用户名 | 手机 | 邮箱）和密码查询用户
     *
     * @param account  用户名 | 手机 | 邮箱
     * @param password 密码
     * @return 验证通过返回签发的 token，否则返回 null
     */
    String login(String account, String password);

    /**
     * 新增用户
     */
    Boolean create(UserQuery userQuery);

    /**
     * 查询用户
     */
    UserVO query(Long id);

    /**
     * 删除用户
     */
    Boolean remove(Long id);

    /**
     * 编辑用户
     */
    Boolean edit(UserQuery userQuery);

    /**
     * 验证用户信息存在性
     *
     * @param type    验证类型：[1]验证用户名 [2]验证手机 [3]验证邮箱
     * @param content 需要验证的内容
     * @return [null]信息不存在
     */
    String verify(Integer type, String content);

}
