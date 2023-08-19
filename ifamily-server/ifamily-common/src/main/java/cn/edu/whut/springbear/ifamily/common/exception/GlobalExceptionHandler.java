package cn.edu.whut.springbear.ifamily.common.exception;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return CommonResult.serviceUnavailable();
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
        } else if (e instanceof MethodArgumentNotValidException) {
            // 412
            String validateMsg = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return CommonResult.preconditionFailed(validateMsg);
        }

        log.error(e.getMessage());
        return CommonResult.serverInternalError();
    }

}
