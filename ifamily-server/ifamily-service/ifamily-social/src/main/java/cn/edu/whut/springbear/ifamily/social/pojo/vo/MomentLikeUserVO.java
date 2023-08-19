package cn.edu.whut.springbear.ifamily.social.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 20:10
 */
@Data
public class MomentLikeUserVO implements Serializable {

    private static final long serialVersionUID = -6065155522300837471L;

    private Long id;

    private String username;

    private String nickname;

    private String avatar;

    private Long userId;

}
