package cn.edu.whut.springbear.ifamily.common.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 09:41
 */
@Getter
public enum SuccessStatusEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 失败
     */
    FAILED(1, "失败");

    private final Integer code;
    private final String desc;

    SuccessStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
