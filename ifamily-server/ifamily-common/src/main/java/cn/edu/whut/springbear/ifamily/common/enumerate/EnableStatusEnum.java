package cn.edu.whut.springbear.ifamily.common.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 08:37
 */
@Getter
public enum EnableStatusEnum {

    /**
     * 启用
     */
    ENABLE(0, "启用"),
    /**
     * 禁用
     */
    DISABLE(1, "禁用");

    private final Integer code;
    private final String desc;

    EnableStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
