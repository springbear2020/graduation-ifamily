package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import lombok.Data;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 10:09
 */
@Data
public class GenealogyMemberTreeBO {

    /**
     * 家族人员 ID
     */
    private Long id;

    /**
     * 家族人员姓名
     */
    private String name;

    /**
     * 家族人员肖像图片地址
     */
    private String portrait;

    /**
     * 是否展开子节点：默认为 false
     */
    private final Boolean extend = false;

    /**
     * 伴侣列表
     */
    private List<GenealogyMemberTreeBO> mate;

    /**
     * 孩子节点列表
     */
    private List<GenealogyMemberTreeBO> children;

}
