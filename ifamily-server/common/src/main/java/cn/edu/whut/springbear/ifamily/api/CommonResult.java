package cn.edu.whut.springbear.ifamily.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 描述消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功返回统一消息
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回自定义消息
     *
     * @param message 自定义消息
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功返回数据和统一消息
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回数据和自定义消息
     *
     * @param data    获取的数据
     * @param message 自定义消息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回统一消息
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    /**
     * 失败返回自定义消息
     *
     * @param message 自定义消息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 未登录
     */
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    /**
     * 未登录返回数据
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权
     */
    public static <T> CommonResult<T> forbidden() {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), null);
    }

    /**
     * 未授权返回数据
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
