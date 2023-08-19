package cn.edu.whut.springbear.ifamily.user.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/04/14 10:35
 */
@Getter
public enum LoginTypeEnum {

    /**
     * 密码登录
     */
    PASSWORD(1, "密码登录"),
    /**
     * 验证码登录
     */
    VERIFY_CODE(2, "验证码登录");

    private final Integer code;
    private final String desc;

    LoginTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
