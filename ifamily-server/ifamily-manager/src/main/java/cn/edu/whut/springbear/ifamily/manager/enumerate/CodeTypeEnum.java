package cn.edu.whut.springbear.ifamily.manager.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 13:17
 */
@Getter
public enum CodeTypeEnum {

    /**
     * 验证码发送记录类型：[0]邮箱验证码
     */
    EMAIL(0, "邮箱验证码"),
    /**
     * 验证码发送记录类型：[1]手机验证码
     */
    PHONE(1, "手机验证码");

    private final Integer code;
    private final String desc;

    CodeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
