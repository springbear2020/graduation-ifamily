package cn.edu.whut.springbear.ifamily.auth.pojo.dto;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:30
 */
@Data
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = -8125835718342819423L;

    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号是否禁用：[0]否 [1]是
     */
    private Boolean enabled;
    /**
     * 登录客户端 ID
     */
    private String clientId;
    /**
     * 用户权限列表
     */
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public SecurityUser(UserDTO userDTO) {
        this.setId(userDTO.getId());
        this.setUsername(userDTO.getUsername());
        this.setPassword(userDTO.getPassword());
        // 账号是否禁用：[0]否 [1]是。未禁用 <=> 启用
        this.setEnabled(AssertEnum.NO.getCode().equals(userDTO.getStatus()));
        this.setClientId(userDTO.getClientId());
        List<String> roles = userDTO.getRoles();
        roles = roles == null ? new ArrayList<>() : roles;
        roles.forEach(role -> this.authorities.add(new SimpleGrantedAuthority(role)));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.enabled;
    }

}
