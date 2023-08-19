package cn.edu.whut.springbear.ifamily.common.exception;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * 503
     */
    @ExceptionHandler(SystemServiceException.class)
    public CommonResult<String> systemService(SystemServiceException e) {
        log.error(e.getMessage());
        return CommonResult.serviceUnavailable(MessageConstants.SYSTEM_EXCEPTION);
    }

    /**
     * 412
     */
    @ExceptionHandler(IncorrectConditionException.class)
    public CommonResult<String> preconditionFailed(IncorrectConditionException e) {
        return CommonResult.preconditionFailed(e.getMessage());
    }

    /**
     * 406
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> handleValidException(MethodArgumentNotValidException e) {
        return CommonResult.failed(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 406
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<String> handleBindException(BindException e) {
        return CommonResult.failed(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 406
     */
    @ExceptionHandler(ValidationException.class)
    public CommonResult<String> handleValidationException(ValidationException e) {
        return CommonResult.failed(e.getCause().getMessage());
    }

    /**
     * 500
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> exception(Exception e) {
        log.error(e.getMessage());
        return CommonResult.serverInternalError(MessageConstants.SYSTEM_EXCEPTION);
    }

}
