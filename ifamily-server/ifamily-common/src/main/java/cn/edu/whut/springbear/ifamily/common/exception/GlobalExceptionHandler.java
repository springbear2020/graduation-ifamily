package cn.edu.whut.springbear.ifamily.common.exception;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 13:09
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 412
     */
    @ExceptionHandler(IllegalConditionException.class)
    public CommonResult<String> preconditionFailed(IllegalConditionException e) {
        return CommonResult.preconditionFailed(e.getMessage());
    }

    /**
     * 403
     */
    @ExceptionHandler(IllegalStatusException.class)
    public CommonResult<String> illegalStatus(IllegalStatusException e) {
        return CommonResult.forbidden(e.getMessage());
    }

    /**
     * 503
     */
    @ExceptionHandler(SystemServiceException.class)
    public CommonResult<String> sqlExecution(SystemServiceException e) {
        log.error(e.getMessage());
        return CommonResult.serviceUnavailable(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    /**
     * 被动抛出的异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> exception(Exception e) {
        if (e instanceof MissingServletRequestParameterException) {
            // [400]缺少必要请求参数
            return CommonResult.badRequest(e.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            // [405]不支持的请求方法
            return CommonResult.methodNotAllowed(e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            // [412]Controller 方法参数无效
            return CommonResult.preconditionFailed(((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else if (e instanceof BindException) {
            // [412]Controller 方法参数值类型正确，但绑定失败
            return CommonResult.preconditionFailed(((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else if (e instanceof ValidationException) {
            // [412]Controller 方法参数值类型错误
            return CommonResult.preconditionFailed(e.getCause().getMessage());
        }

        log.error(e.getMessage());
        return CommonResult.serverInternalError(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

}
