package cn.edu.whut.springbear.ifamily.common.constant;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:52
 */
public class AuthConstants {

    /**
     * 后台管理
     */
    public static final String CLIENT_ADMIN_ID = "backend-app";
    /**
     * 移动应用
     */
    public static final String CLIENT_MOBILE_ID = "mobile-app";
    /**
     * 认证信息 Http 请求头
     */
    public static final String JWT_TOKEN_HEADER = "Authorization";
    /**
     * 用户信息 Http 请求头
     */
    public static final String GENERAL_USER_HEADER = "General-User";
    /**
     * JWT 存储权限前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";
    /**
     * Redis 缓存权限规则 key
     */
    public static final String PERMISSION_ROLES_MAP_KEY = "auth:permissionRoleNamesMap";
    /**
     * 后台管理路径模式匹配
     */
    public static final String ADMIN_URL_PATTERN = "/**/backend/**";
    /**
     * 权限校验通用密码
     */
    public static final String COMMON_AUTH_PASSWORD = "ifamily";

}
