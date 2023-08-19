package cn.edu.whut.springbear.ifamily.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 14:22
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -2219180158488500620L;

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String nickname;

    private String avatar;

    private String signature;

    private List<String> roles;

}
