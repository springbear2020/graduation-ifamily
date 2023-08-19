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
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),
    /**
     * 未授权
     */
    FORBIDDEN(403, "权限不足"),
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "未登录或 token 已过期");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
