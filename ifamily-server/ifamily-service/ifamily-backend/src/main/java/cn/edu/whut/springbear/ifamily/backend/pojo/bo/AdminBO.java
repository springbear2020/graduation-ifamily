package cn.edu.whut.springbear.ifamily.backend.pojo.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/04 09:08
 */
@Data
public class AdminBO implements Serializable {

    private static final long serialVersionUID = -3078463269730183396L;

    private Long id;

    private String username;

    private String avatar;

    private String nickname;

    private String signature;

    private List<String> menus;

}
