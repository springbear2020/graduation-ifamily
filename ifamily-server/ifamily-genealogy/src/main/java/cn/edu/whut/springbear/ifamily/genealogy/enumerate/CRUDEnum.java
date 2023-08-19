package cn.edu.whut.springbear.ifamily.genealogy.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/04/07 11:53
 */
@Getter
public enum CRUDEnum {

    /**
     * [1]新增
     */
    INSERT(1, "新增"),
    /**
     * [2]删除
     */
    DELETE(2, "删除"),
    /**
     * [3]编辑
     */
    UPDATE(3, "编辑"),
    /**
     * [4]查看
     */
    SELECT(4, "查看");

    private final Integer code;
    private final String desc;

    CRUDEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
