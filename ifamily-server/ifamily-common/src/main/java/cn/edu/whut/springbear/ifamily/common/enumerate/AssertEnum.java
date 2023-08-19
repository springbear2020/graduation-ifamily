package cn.edu.whut.springbear.ifamily.common.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:50
 */
@Getter
public enum AssertEnum {

    /**
     * [1]是
     */
    YES(1, "是"),
    /**
     * [0]否
     */
    NO(0, "否");

    private final Integer code;
    private final String desc;

    AssertEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
