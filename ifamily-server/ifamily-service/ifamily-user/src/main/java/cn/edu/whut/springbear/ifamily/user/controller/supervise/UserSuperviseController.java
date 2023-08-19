package cn.edu.whut.springbear.ifamily.user.controller.supervise;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.LoginLogVO;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import cn.edu.whut.springbear.ifamily.user.service.LoginLogService;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/21 10:26
 */
@AllArgsConstructor
@Api(tags = "用户监督接口")
@RestController
@RequestMapping("/user/supervise")
public class UserSuperviseController {

    private final UserService userService;
    private final LoginLogService loginLogService;

    @ApiOperation("获取当前登录用户")
    @GetMapping
    public CommonResult<Object> current() {
        UserVO userVO = this.userService.current();
        return userVO == null ? CommonResult.failed("用户信息无数据") : CommonResult.success(userVO);
    }

    @ApiOperation("注销用户账号")
    @DeleteMapping
    public CommonResult<String> logout(@ApiParam("账号登录密码") @RequestParam("password") String password) {
        UserVO current = this.userService.current();
        boolean deleteResult = this.userService.logout(current.getId(), password);
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求注销账号失败");
    }

    @ApiOperation("更新用户资料")
    @PutMapping("/profile/{type}")
    public CommonResult<String> updateSimpleProfile(
            @ApiParam("需要保存的新内容") @RequestParam("content") String content,
            @ApiParam("内容类型：[1]用户昵称 [2]个性签名 [3]头像地址") @PathVariable("type") Integer type) {

        UserVO current = this.userService.current();
        boolean updateResult = this.userService.updateSimpleProfile(type, current.getId(), content);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新资料失败");
    }

    @ApiOperation("更新用户隐私")
    @PutMapping("/privacy/{type}")
    public CommonResult<String> updatePrivacyProfile(
            @ApiParam("内容类型：[1]用户名 [2]邮箱 [3]手机") @PathVariable("type") Integer type,
            @ApiParam("需要保存的新内容") @RequestParam("content") String content,
            @ApiParam("额外携带的信息：[type=1]携带用户密码 [type=2 || type=3]携带验证码") @RequestParam("extra") String extra) {

        UserVO current = this.userService.current();
        boolean updateResult;
        // 操作类型：[1]用户名 [2]邮箱 [3]手机
        switch (type) {
            case 1:
                updateResult = this.userService.updateUsername(current.getId(), content, extra);
                break;
            case 2:
                updateResult = this.userService.updateEmail(current.getId(), content, extra);
                break;
            case 3:
                updateResult = this.userService.updatePhone(current.getId(), content, extra);
                break;
            default:
                return CommonResult.preconditionFailed("内容类型：[1]用户名 [2]邮箱 [3]手机");
        }

        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新隐私失败");
    }

    @ApiOperation("查询用户登录记录分页数据")
    @GetMapping("/devices")
    public CommonResult<Object> loginLog(@Validated PageQuery pageQuery) {
        UserVO current = this.userService.current();
        List<LoginLogVO> records = this.loginLogService.page(pageQuery, current.getId());
        return records == null || records.isEmpty() ? CommonResult.failed("登录记录无数据") : CommonResult.success(records);
    }

}
