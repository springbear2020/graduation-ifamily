package cn.edu.whut.springbear.ifamily.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/05/10 21:46
 */
@Data
public class AdminUserVO implements Serializable {

    private static final long serialVersionUID = 1197742354964052237L;

    private Long id;

    private String username;

    private String nickname;

    private String signature;

    private String avatar;

    private Integer status;

    private Date lastLogin;

    private Date created;

    private Date modified;

}
