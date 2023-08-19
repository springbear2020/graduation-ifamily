package cn.edu.whut.springbear.ifamily.manager.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/04/14 10:45
 */
@Getter
public enum CodeTypeEnum {

    /**
     * 邮箱验证码
     */
    EMAIL_TYPE(1, "邮箱验证码"),
    /**
     * 短信验证码
     */
    SMS_TYPE(2, "短信验证码");

    private final Integer code;
    private final String desc;

    CodeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
