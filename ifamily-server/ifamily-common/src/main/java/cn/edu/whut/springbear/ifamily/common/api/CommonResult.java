package cn.edu.whut.springbear.ifamily.common.api;

import lombok.Data;

/**
 * 通用结果返回封装类
 *
 * @author Spring-_-Bear
 * @since 23/03/11 10:10
 */
@Data
public final class CommonResult<T> {

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
     * 成功
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /// ================================================================================================================

    /**
     * 身份认证
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 拒绝访问
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCodeEnum.FORBIDDEN.getCode(), ResultCodeEnum.FORBIDDEN.getMessage(), data);
    }

    /// ================================================================================================================

    /**
     * 内部异常
     */
    public static <T> CommonResult<T> serverInternalError() {
        return new CommonResult<>(ResultCodeEnum.SERVER_INTERNAL_ERROR.getCode(), ResultCodeEnum.SERVER_INTERNAL_ERROR.getMessage(), null);
    }

    /**
     * 服务不可用
     */
    public static <T> CommonResult<T> serviceUnavailable() {
        return new CommonResult<>(ResultCodeEnum.SERVICE_UNAVAILABLE.getCode(), ResultCodeEnum.SERVICE_UNAVAILABLE.getMessage(), null);
    }

    /// ===============================================================================================================

    /**
     * 失败
     */
    public static <T> CommonResult<T> failed(T data) {
        return new CommonResult<>(ResultCodeEnum.NOT_ACCEPTABLE.getCode(), ResultCodeEnum.NOT_ACCEPTABLE.getMessage(), data);
    }

    /**
     * 错误条件
     */
    public static <T> CommonResult<T> preconditionFailed(T data) {
        return new CommonResult<>(ResultCodeEnum.PRECONDITION_FAILED.getCode(), ResultCodeEnum.PRECONDITION_FAILED.getMessage(), data);
    }

    /**
     * 缺少参数
     */
    public static <T> CommonResult<T> badRequest(T data) {
        return new CommonResult<>(ResultCodeEnum.BAD_REQUEST.getCode(), ResultCodeEnum.BAD_REQUEST.getMessage(), data);
    }

    /**
     * 非法方法
     */
    public static <T> CommonResult<T> methodNotAllowed(T data) {
        return new CommonResult<>(ResultCodeEnum.METHOD_NOT_ALLOWED.getCode(), ResultCodeEnum.METHOD_NOT_ALLOWED.getMessage(), data);
    }

}
