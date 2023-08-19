package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.ResetQuery;
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
    String login(LoginQuery loginQuery);

    /**
     * 用户注册
     *
     * @return 注册成功返回签发的 token，否则返回 null
     */
    String register(ResetQuery resetQuery);

    /**
     * 重置用户登录密码
     */
    boolean reset(ResetQuery resetQuery);

    /**
     * 返回当前请求中已认证的用户信息
     */
    UserVO current();

    /**
     * 更新用户简单资料：头像地址、用户昵称、用户签名
     */
    boolean updateSimpleProfile(UserQuery userQuery);

    /**
     * 更新用户名
     *
     * @param userId      用户 ID
     * @param newUsername 新用户名
     * @param password    用户账号登录密码
     * @return [true]更新成功
     */
    boolean updateUsername(Long userId, String newUsername, String password);

    /**
     * 更新邮箱
     *
     * @param userId   用户 ID
     * @param newEmail 新邮箱地址
     * @param code     验证码
     * @return [true]更新成功
     */
    boolean updateEmail(Long userId, String newEmail, String code);

    /**
     * 更新手机号
     *
     * @param userId   用户 ID
     * @param newPhone 新手机号
     * @param code     验证码
     * @return [true]更新成功
     */
    boolean updatePhone(Long userId, String newPhone, String code);

    /**
     * 用户账号注销
     */
    boolean remove(Long userId, String password);

    /**
     * 根据用户名查询用户
     */
    UserDO getUserByUsername(String username);

}
