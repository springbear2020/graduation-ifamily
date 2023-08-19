package cn.edu.whut.springbear.ifamily.genealogy.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/04/03 18:17
 */
@Getter
public enum GenderEnum {

    /**
     * 男
     */
    MALE(0, "男"),
    /**
     * 女
     */
    FEMALE(1, "女");

    private final Integer code;
    private final String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
