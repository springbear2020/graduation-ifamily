package cn.edu.whut.springbear.ifamily.api;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 10:03
 */
@Getter
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),
    /**
     * 暂未登录或 token 已过期
     */
    UNAUTHORIZED(401, "暂未登录或 token 已过期"),
    /**
     * 权限不足
     */
    FORBIDDEN(403, "权限不足");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}