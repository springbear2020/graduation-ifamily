package cn.edu.whut.springbear.ifamily.genealogy.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/31 11:18
 */
@Data
public class PeopleCardVO implements Serializable {

    private static final long serialVersionUID = 6448083277960081257L;

    private Long id;

    private String portrait;

    private String name;

    private Integer gender;

    private Date deathDate;

}
