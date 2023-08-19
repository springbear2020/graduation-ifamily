package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.common.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.user.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.LoginLogVO;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import cn.edu.whut.springbear.ifamily.user.service.LoginLogService;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import cn.hutool.core.util.ReUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/21 10:26
 */
@Api(tags = "用户监督管理接口")
@RestController
@RequestMapping("/supervise/user")
public class UserSuperviseController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginLogService loginLogService;

    @ApiOperation("获取当前用户")
    @GetMapping
    public CommonResult<Object> current() {
        UserVO userVO = this.userService.current();
        return userVO != null ? CommonResult.success(userVO) : CommonResult.failed(UserMessageConstants.UNAUTHORIZED);
    }

    @ApiOperation("查询登录记录")
    @GetMapping("/devices")
    public CommonResult<Object> loginLog(@Validated PageQuery pageQuery) {
        UserVO current = this.userService.current();
        List<LoginLogVO> records = this.loginLogService.page(pageQuery, current.getId());
        return records == null || records.isEmpty() ? CommonResult.failed("登录记录无数据") : CommonResult.success(records);
    }

    /**
     * 用户账号注销
     */
    @ApiOperation("用户账号注销")
    @DeleteMapping
    public CommonResult<String> logout(@ApiParam("账号登录密码") @RequestParam("password") String password) {
        UserVO current = this.userService.current();
        boolean deleteResult = this.userService.remove(current.getId(), password);
        return deleteResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    /**
     * 更新用户简单资料
     *
     * @param content 需要保存的新内容
     * @param type    操作类型：[1]用户昵称 [2]个性签名 [3]头像地址
     */
    @ApiOperation("更新用户资料")
    @PutMapping("/profile/{type}")
    public CommonResult<String> updateSimpleProfile(
            @ApiParam("需要保存的新内容") @RequestParam("content") String content,
            @ApiParam("操作类型：[1]用户昵称 [2]个性签名 [3]头像地址") @PathVariable("type") Integer type) {

        UserQuery userQuery = new UserQuery();
        UserVO current = this.userService.current();
        userQuery.setId(current.getId());

        final int limit = 30;
        switch (type) {
            case 1:
                // 用户昵称
                if (!StringUtils.hasLength(content) || content.length() > limit) {
                    return CommonResult.failed("请填写用户昵称, 长度不大于 30");
                }
                userQuery.setNickname(content);
                break;
            case 2:
                // 个性签名
                if (!StringUtils.hasLength(content) || content.length() > limit) {
                    return CommonResult.failed("请填写个性签名, 长度不大于 30");
                }
                userQuery.setSignature(content);
                break;
            case 3:
                // 头像地址
                String urlRegexp = "^((https?|http|ftp|file):\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
                if (!ReUtil.isMatch(urlRegexp, content)) {
                    return CommonResult.failed("用户头像图片地址不合法");
                }
                userQuery.setAvatar(content);
                break;
            default:
                return CommonResult.failed("类型：[1]用户昵称 [2]个性签名 [3]头像地址");
        }

        boolean updateResult = this.userService.updateSimpleProfile(userQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    /**
     * 更新用户隐私信息
     *
     * @param type    操作类型：[1]用户名 [2]邮箱 [3]手机
     * @param content 需要保存的新内容
     * @param extra   额外携带的信息：[type==1]携带用户密码 [type==2 || type=3]携带验证码
     */
    @ApiOperation("更新用户隐私")
    @PutMapping("/privacy/{type}")
    public CommonResult<String> updatePrivacyProfile(
            @ApiParam("操作类型：[1]用户名 [2]邮箱 [3]手机") @PathVariable("type") Integer type,
            @ApiParam("需要保存的新内容") @RequestParam("content") String content,
            @ApiParam("额外携带的信息：[type==1]携带用户密码 [type==2 || type=3]携带验证码") @RequestParam("extra") String extra) {

        UserVO current = this.userService.current();
        boolean updateResult;
        switch (type) {
            case 1:
                // 用户名
                String usernameRegexp = "^[a-zA-Z]([-_a-zA-Z0-9]{4,19})+$";
                if (!ReUtil.isMatch(usernameRegexp, content)) {
                    return CommonResult.failed("用户名格式：以字母开头，可包含字母、数字、下划线和连字符，长度限 5 至 20 位");
                }
                updateResult = this.userService.updateUsername(current.getId(), content, extra);
                break;
            case 2:
                // 邮箱
                String emailRegexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
                if (!ReUtil.isMatch(emailRegexp, content)) {
                    return CommonResult.failed("请输入正确格式的邮箱地址");
                }
                updateResult = this.userService.updateEmail(current.getId(), content, extra);
                break;
            case 3:
                // 手机号
                String phoneRegexp = "^1[3456789]\\d{9}$";
                if (!ReUtil.isMatch(phoneRegexp, content)) {
                    return CommonResult.failed("请输入正确格式的手机号");
                }
                updateResult = this.userService.updatePhone(current.getId(), content, extra);
                break;
            default:
                return CommonResult.failed("类型：[1]用户名 [2]邮箱 [3]手机");
        }

        return updateResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

}
