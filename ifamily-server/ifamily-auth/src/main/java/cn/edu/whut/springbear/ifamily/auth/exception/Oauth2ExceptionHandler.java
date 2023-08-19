package cn.edu.whut.springbear.ifamily.auth.exception;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:59
 */
@RestControllerAdvice
public class Oauth2ExceptionHandler {

    @ExceptionHandler(value = OAuth2Exception.class)
    public CommonResult<Object> handleOauth2(OAuth2Exception e) {
        return CommonResult.failed(e.getMessage());
    }

}
