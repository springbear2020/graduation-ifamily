package cn.edu.whut.springbear.ifamily.common.exception;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 13:09
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult<String> illegalArgument(IllegalArgumentException e) {
        return CommonResult.badRequest(e.getMessage());
    }

    /**
     * 403
     */
    @ExceptionHandler(IllegalStateException.class)
    public CommonResult<String> illegalState(IllegalStateException e) {
        return CommonResult.forbidden(e.getMessage());
    }

    /**
     * 503
     */
    @ExceptionHandler(SqlExecutionException.class)
    public CommonResult<String> sqlExecution(SqlExecutionException e) {
        log.error(e.getMessage());
        return CommonResult.serviceUnavailable(e.getMessage());
    }

    /**
     * 被动抛出的异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> exception(Exception e) {
        if (e instanceof MissingServletRequestParameterException) {
            // 400
            return CommonResult.badRequest(e.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            // 405
            return CommonResult.methodNotAllowed(e.getMessage());
        }

        log.error(e.getMessage());
        return CommonResult.serverInternalError(e.getMessage());
    }

}
