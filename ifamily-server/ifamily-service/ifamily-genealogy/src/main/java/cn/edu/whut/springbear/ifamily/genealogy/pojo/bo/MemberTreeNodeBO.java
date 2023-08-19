package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import lombok.Data;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 10:09
 */
@Data
public class MemberTreeNodeBO {

    private Long id;

    private String name;

    private Integer gender;

    private String portrait;

    /**
     * 是否展开子节点：默认为 false
     */
    private final Boolean extend = false;

    /**
     * 伴侣列表
     */
    private List<MemberTreeNodeBO> mates;

    /**
     * 孩子节点列表
     */
    private List<MemberTreeNodeBO> children;

}
