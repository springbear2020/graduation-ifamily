package cn.edu.whut.springbear.ifamily.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 09:54
 */
@Data
public class CommonUserVO implements Serializable {

    private static final long serialVersionUID = -4087299664142857145L;

    private Long id;

    private String username;

    private String nickname;

    private String avatar;

}
