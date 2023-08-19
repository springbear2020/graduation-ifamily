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
     * 未授权
     */
    FORBIDDEN(403, "拒绝访问"),
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "账号未登录或令牌已过期"),
    /**
     * 非法参数
     */
    BAD_REQUEST(400, "无效的请求参数"),
    /**
     * 非法请求方法
     */
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),
    /**
     * 服务器内部异常
     */
    SERVER_INTERNAL_ERROR(500, "服务器内部异常"),
    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务器服务暂不可用");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
