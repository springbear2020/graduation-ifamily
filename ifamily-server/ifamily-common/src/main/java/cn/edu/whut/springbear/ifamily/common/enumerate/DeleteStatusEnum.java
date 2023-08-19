package cn.edu.whut.springbear.ifamily.common.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 12:37
 */
@Getter
public enum DeleteStatusEnum {

    /**
     * 未删除
     */
    UNDELETED(0, "未删除"),
    /**
     * 已删除
     */
    DELETED(1, "已删除");

    private final Integer code;
    private final String desc;

    DeleteStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
