package cn.edu.whut.springbear.ifamily.genealogy.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:50
 */
@Getter
public enum DefaultStatusEnum {

    /**
     * 是否是用户默认家族：[1]是
     */
    YES(1, "用户默认家族"),
    /**
     * 是否是用户默认家族：[0]不是
     */
    NO(0, "不是用户默认家族");

    private final Integer code;
    private final String desc;

    DefaultStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
