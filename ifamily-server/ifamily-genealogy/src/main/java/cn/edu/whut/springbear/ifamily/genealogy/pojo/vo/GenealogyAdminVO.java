package cn.edu.whut.springbear.ifamily.genealogy.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 19:28
 */
@Data
public class GenealogyAdminVO implements Serializable {

    private static final long serialVersionUID = -1965015968159745568L;

    /**
     * 家族人员 ID
     */
    private Long id;

    /**
     * 人员姓名
     */
    private String name;

    /**
     * 性别：[0]男 [1]女
     */
    private Integer gender;

}
