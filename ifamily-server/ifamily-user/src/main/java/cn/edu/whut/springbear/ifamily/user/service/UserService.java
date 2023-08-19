package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserLoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserResetQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserService extends IService<UserDO> {

    /**
     * 用户登录
     *
     * @return 登录成功返回签发的 token，否则返回 null
     */
    String login(UserLoginQuery userLoginQuery);

    /**
     * 用户注册
     *
     * @return 注册成功返回签发的 token，否则返回 null
     */
    String register(UserResetQuery userResetQuery);

    /**
     * 重置用户登录密码
     *
     * @return [true]重置成功
     */
    boolean reset(UserResetQuery userResetQuery);

    /**
     * 返回当前请求中已认证的用户信息
     */
    UserVO currentUser();

    /**
     * 更新用户简单资料，如用户头像地址、用户昵称、用户签名
     *
     * @return [true]更新成功
     */
    boolean updateSimpleProfile(UserQuery userQuery);

    /**
     * 更新用户名
     *
     * @param username 新用户名
     * @param password 用户账号登录密码
     * @return [true]更新成功
     */
    boolean updateUsername(String username, String password);

    /**
     * 更新邮箱
     *
     * @param email 新邮箱地址
     * @param code  验证码
     * @return [true]更新成功
     */
    boolean updateEmail(String email, String code);

    /**
     * 更新手机号
     *
     * @param phone 新手机号
     * @param code  验证码
     * @return [true]更新成功
     */
    boolean updatePhone(String phone, String code);

    /**
     * 用户账号注销
     */
    boolean userLogout(String password);

    /**
     * 根据用户名查询用户
     */
    UserDO getUserByUsername(String username);

}
