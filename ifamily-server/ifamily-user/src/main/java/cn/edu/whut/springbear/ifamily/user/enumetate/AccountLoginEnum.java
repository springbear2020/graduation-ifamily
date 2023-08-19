package cn.edu.whut.springbear.ifamily.user.enumetate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 09:06
 */
@Getter
public enum AccountLoginEnum {

    /**
     * 密码登录
     */
    PASSWORD(0, "密码登录"),
    /**
     * 验证码登录
     */
    EMAIL(1, "验证码登录");

    private final Integer code;
    private final String desc;

    AccountLoginEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
