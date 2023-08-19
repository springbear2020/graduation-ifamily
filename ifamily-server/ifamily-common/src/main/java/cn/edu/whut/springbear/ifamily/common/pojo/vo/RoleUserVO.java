package cn.edu.whut.springbear.ifamily.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/05/08 10:32
 */
@Data
public class RoleUserVO implements Serializable {

    private static final long serialVersionUID = 1530799298702127526L;

    private Long id;

    private String username;

    private Integer status;

    private Date lastLogin;

}
