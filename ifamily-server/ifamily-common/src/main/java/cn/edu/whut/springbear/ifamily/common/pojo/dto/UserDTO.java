package cn.edu.whut.springbear.ifamily.common.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:38
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 2912571740418610288L;

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
     * 账号禁用状态：[0]启用 [1]禁用
     */
    private Integer status;

    /**
     * 用户对应角色列表
     */
    private List<String> roles;
    /**
     * 登录客户端 ID
     */
    private String clientId;

}
