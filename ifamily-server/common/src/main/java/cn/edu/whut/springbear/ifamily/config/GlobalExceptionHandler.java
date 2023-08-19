package cn.edu.whut.springbear.ifamily.config;

import cn.edu.whut.springbear.ifamily.api.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 11:15
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult<Object> exception(Exception e) {
        return CommonResult.failed(e.getMessage());
    }
}
