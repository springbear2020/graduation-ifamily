package cn.edu.whut.springbear.ifamily.auth.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 15:23
 */
@Data
public class Oauth2TokenVO implements Serializable {

    private static final long serialVersionUID = 2054406405255587244L;

    private String accessToken;

    private String refreshToken;

    private Integer expires;

}
