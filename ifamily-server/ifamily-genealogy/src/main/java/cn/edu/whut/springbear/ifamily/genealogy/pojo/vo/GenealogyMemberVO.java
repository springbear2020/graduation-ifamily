package cn.edu.whut.springbear.ifamily.genealogy.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 11:22
 */
@Data
public class GenealogyMemberVO implements Serializable {
    private static final long serialVersionUID = 8934848314133994593L;

    private Long id;

    private String cover;

    private String title;

    private String surname;

    private String address;

    private String ancestryAddress;

    private String introduction;

    private String generationSong;

    private Date created;

    private Long creatorUserId;

    private Long total;

    private Long male;

    private Long female;

    private Long alive;

    private Long death;

    private Integer defaultGenealogy;

}
