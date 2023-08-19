package cn.edu.whut.springbear.ifamily.genealogy.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 22:36
 */
@Data
public class PeopleVO implements Serializable {

    private static final long serialVersionUID = 2234229965237156379L;

    private String portrait;

    private String surname;

    private String name;

    private Integer gender;

    private Integer generation;

    private String generationName;

    private Integer seniority;

    private String phone;

    private String residence;

    private Date birthdate;

    private Integer lunarBirthdate;

    private String birthplace;

    private Date deathDate;

    private Integer lunarDeathDate;

    private String burialPlace;

    private String familyRelationRemark;

    private Long userId;

    private Long fatherId;

    private Long motherId;

    private Long genealogyId;

}
