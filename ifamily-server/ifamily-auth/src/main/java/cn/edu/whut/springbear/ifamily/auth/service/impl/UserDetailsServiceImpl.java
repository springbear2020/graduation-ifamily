package cn.edu.whut.springbear.ifamily.auth.service.impl;

import cn.edu.whut.springbear.ifamily.auth.pojo.dto.SecurityUser;
import cn.edu.whut.springbear.ifamily.auth.service.feign.AdminFeignService;
import cn.edu.whut.springbear.ifamily.auth.service.feign.UserFeignService;
import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.constant.GlobalMessageConstants;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:50
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminFeignService adminFeignService;
    private final UserFeignService userFeignService;
    private final HttpServletRequest httpServletRequest;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = this.httpServletRequest.getParameter("client_id");
        if (!StringUtils.hasLength(clientId)) {
            throw new OAuth2Exception(GlobalMessageConstants.INCORRECT_CLIENT_ID);
        }

        UserDTO userDTO;
        // 根据不同的客户端调用不同远程服务验证用户名存在性
        if (AuthConstants.CLIENT_ADMIN_ID.equals(clientId)) {
            userDTO = this.adminFeignService.loadAdminByUsername(username);
        } else if (AuthConstants.CLIENT_MOBILE_ID.equals(clientId)) {
            userDTO = this.userFeignService.loadUserByUsername(username);
        } else {
            throw new OAuth2Exception(GlobalMessageConstants.INCORRECT_CLIENT_ID);
        }

        if (userDTO.getId() == null) {
            throw new OAuth2Exception(GlobalMessageConstants.USERNAME_NOT_EXISTS);
        }

        userDTO.setClientId(clientId);
        SecurityUser securityUser = new SecurityUser(userDTO);
        if (!securityUser.isEnabled()) {
            throw new OAuth2Exception(GlobalMessageConstants.ACCOUNT_WAS_DISABLED);
        }

        return securityUser;
    }

}
