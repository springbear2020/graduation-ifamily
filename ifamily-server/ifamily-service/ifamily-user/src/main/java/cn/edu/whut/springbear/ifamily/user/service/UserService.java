package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.ResetQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户
     */
    UserDTO loadUserByUsername(String username);

    /**
     * 用户登录
     *
     * @return 登录成功返回签发的 token
     */
    CommonResult<Object> login(LoginQuery loginQuery);

    /**
     * 用户注册
     *
     * @return 注册成功返回签发的 token
     */
    CommonResult<Object> register(ResetQuery resetQuery);

    /**
     * 重置用户登录密码
     */
    boolean reset(ResetQuery resetQuery);

    /**
     * 返回当前登录系统的用户信息
     */
    UserVO current();

    /**
     * 用户账号注销
     */
    boolean logout(Long userId, String password);

    /**
     * 更新用户简单资料：头像地址、用户昵称、用户签名
     *
     * @param type    类型：[1]用户昵称 [2]个性签名 [3]头像地址
     * @param userId  用户 ID
     * @param content 需要更新的新内容
     */
    boolean updateSimpleProfile(Integer type, Long userId, String content);

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

}
