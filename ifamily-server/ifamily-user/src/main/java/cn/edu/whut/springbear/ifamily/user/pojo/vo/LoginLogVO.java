package cn.edu.whut.springbear.ifamily.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/24 22:01
 */
@Data
public class LoginLogVO implements Serializable {

    private static final long serialVersionUID = -763271002066443139L;

    private Long id;

    private String ip;

    private String location;

    private String device;

    private Date loginDatetime;

}
