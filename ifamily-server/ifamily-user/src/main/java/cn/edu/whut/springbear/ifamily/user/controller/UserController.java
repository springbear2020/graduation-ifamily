package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.exception.SystemServiceException;
import cn.edu.whut.springbear.ifamily.user.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/21 10:26
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/manage/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public CommonResult<Object> userDelete(@PathVariable Long id) {
        // 删除用户前先验证用户是否存在
        UserVO userVO = this.userService.query(id);
        if (userVO == null) {
            return CommonResult.failed(UserMessageConstants.USER_NOT_EXISTS);
        }
        if (this.userService.remove(id)) {
            return CommonResult.success();
        }
        throw new SystemServiceException(UserMessageConstants.SQL_EXECUTION_ERROR);
    }

    @ApiOperation("编辑用户")
    @PutMapping
    public CommonResult<Object> editUser(@RequestBody UserQuery userQuery) {
        // 编辑用户前先验证用户是否存在
        UserVO userVO = this.userService.query(userQuery.getId());
        if (userVO == null) {
            return CommonResult.failed(UserMessageConstants.USER_NOT_EXISTS);
        }
        if (this.userService.edit(userQuery)) {
            return CommonResult.success();
        }
        throw new SystemServiceException(UserMessageConstants.SQL_EXECUTION_ERROR);
    }

    @ApiOperation("查询用户")
    @GetMapping("/{id}")
    public CommonResult<Object> queryUser(@PathVariable Long id) {
        UserVO userVO = this.userService.query(id);
        return userVO == null ? CommonResult.failed(UserMessageConstants.USER_NOT_EXISTS) : CommonResult.success(userVO);
    }

}
