package cn.edu.whut.springbear.ifamily.backend.service.impl;

import cn.edu.whut.springbear.ifamily.backend.mapper.AdminMapper;
import cn.edu.whut.springbear.ifamily.backend.pojo.bo.AdminBO;
import cn.edu.whut.springbear.ifamily.backend.pojo.po.AdminDO;
import cn.edu.whut.springbear.ifamily.backend.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.backend.service.AdminService;
import cn.edu.whut.springbear.ifamily.backend.service.LoginLogService;
import cn.edu.whut.springbear.ifamily.backend.service.feign.AdminRoleFeignService;
import cn.edu.whut.springbear.ifamily.backend.service.feign.AuthFeignService;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.api.ResultCodeEnum;
import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.RoleUserVO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:32
 */
@AllArgsConstructor
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminDO> implements AdminService {

    private final AdminRoleFeignService adminRoleFeignService;
    private final AuthFeignService authFeignService;
    private final HttpServletRequest httpServletRequest;
    private final LoginLogService loginLogService;

    @Override
    public UserDTO loadAdminByUsername(String username) {
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        AdminDO adminDO = this.baseMapper.selectOne(queryWrapper);
        if (adminDO == null) {
            return null;
        }

        // DO -> DTO
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(adminDO, userDTO);

        // 查询管理员对应的角色列表
        List<String> adminRoleNames = this.adminRoleFeignService.listRoleNamesOfAdmin(userDTO.getId());
        userDTO.setRoles(adminRoleNames);

        return userDTO;
    }

    @Override
    public CommonResult<Object> login(LoginQuery loginQuery) {
        // 封装认证服务器所需必要请求参数
        Map<String, String> params = new HashMap<>(6);
        params.put("grant_type", "password");
        params.put("client_id", AuthConstants.CLIENT_ADMIN_ID);
        params.put("client_secret", AuthConstants.COMMON_AUTH_PASSWORD);
        params.put("username", loginQuery.getUsername());
        params.put("password", loginQuery.getPassword());

        // 请求认证服务器签发令牌
        CommonResult<Object> tokenResponse = this.authFeignService.postAccessToken(params);
        // 签发令牌失败，直接返回失败结果
        if (!tokenResponse.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return tokenResponse;
        }

        // 保存管理员登录记录
        UserDTO userDTO = this.loadAdminByUsername(loginQuery.getUsername());
        this.loginLogService.create(userDTO.getId());
        // 更新管理员上次登录时间
        AdminDO adminDO = new AdminDO();
        adminDO.setId(userDTO.getId());
        adminDO.setLastLogin(new Date());
        this.updateById(adminDO);

        return tokenResponse;
    }

    @Override
    public AdminBO current() {
        // 从请求头中获取已通过认证的用户信息
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        AdminDO adminDO = this.getById(userDTO.getId());
        AdminBO adminBO = new AdminBO();
        BeanUtils.copyProperties(adminDO, adminBO);

        // 查询管理员对应的角色名称集合
        List<String> adminRoles = this.adminRoleFeignService.listMenuPathsOfAdmin(userDTO.getId());
        adminBO.setMenus(adminRoles);

        return adminBO;
    }

    @Override
    public List<RoleUserVO> listInBatchIds(List<Long> adminIds) {
        if (CollUtil.isEmpty(adminIds)) {
            return null;
        }

        List<AdminDO> adminDOList = this.listByIds(adminIds);
        if (CollUtil.isEmpty(adminDOList)) {
            return null;
        }

        // DO -> VO
        List<RoleUserVO> resultList = new ArrayList<>();
        for (AdminDO adminDO : adminDOList) {
            RoleUserVO roleUserVO = new RoleUserVO();
            BeanUtils.copyProperties(adminDO, roleUserVO);
            resultList.add(roleUserVO);
        }

        return resultList;
    }

}
