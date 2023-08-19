package cn.edu.whut.springbear.ifamily.common.api;

import lombok.Getter;

/**
 * 通用结果响应码
 *
 * @author Spring-_-Bear
 * @since 23/03/11 10:03
 */
@Getter
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 失败
     */
    NOT_ACCEPTABLE(406, "操作失败"),
    /**
     * 失败
     */
    PRECONDITION_FAILED(412, "错误的请求条件"),
    /// ================================================================================================================
    /**
     * 服务器异常
     */
    SERVER_INTERNAL_ERROR(500, "服务器异常"),
    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    /// ================================================================================================================
    /**
     * 身份认证
     */
    UNAUTHORIZED(401, "账号未登录或令牌已过期"),
    /**
     * 拒绝访问
     */
    FORBIDDEN(403, "拒绝访问");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
