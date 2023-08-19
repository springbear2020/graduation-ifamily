package cn.edu.whut.springbear.ifamily.model.dto;

import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity 安全框架认证所需用户信息类
 *
 * @author Spring-_-Bear
 * @since 23/03/20 08:33
 */
@Data
@AllArgsConstructor
public class SecurityUserDetailsDTO implements UserDetails {

    private static final long serialVersionUID = 1530174314245829363L;

    /**
     * 用户
     */
    private final UserDO user;

    /**
     * 用户拥有的权限列表
     */
    private final List<PermissionDO> permissionList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissionList.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getId() + ":" + permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return EnableStatusEnum.ENABLE.getCode().equals(this.user.getStatus());
    }

    @Getter
    enum EnableStatusEnum {

        /**
         * 启用
         */
        ENABLE(0, "启用"),
        /**
         * 禁用
         */
        DISABLE(1, "禁用");

        private final Integer code;
        private final String desc;

        EnableStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

}