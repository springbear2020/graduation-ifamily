package cn.edu.whut.springbear.ifamily.common.api;

import lombok.Data;

/**
 * 通用结果返回封装类
 *
 * @author Spring-_-Bear
 * @since 23/03/11 10:10
 */
@Data
public class CommonResult<T> {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 描述消息
     */
    private String desc;
    /**
     * 返回数据
     */
    private T data;

    private CommonResult(Integer code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    /**
     * 成功返回统一消息
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回统一消息和数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 失败返回统一消息
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), null);
    }

    /**
     * 失败返回自定义消息
     */
    public static <T> CommonResult<T> failed(String desc) {
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), desc, null);
    }

    /**
     * 未授权自定义消息
     */
    public static <T> CommonResult<T> forbidden(String desc) {
        return new CommonResult<>(ResultCodeEnum.FORBIDDEN.getCode(), desc, null);
    }

    /**
     * 未认证自定义消息
     */
    public static <T> CommonResult<T> unauthorized(String desc) {
        return new CommonResult<>(ResultCodeEnum.UNAUTHORIZED.getCode(), desc, null);
    }

}
