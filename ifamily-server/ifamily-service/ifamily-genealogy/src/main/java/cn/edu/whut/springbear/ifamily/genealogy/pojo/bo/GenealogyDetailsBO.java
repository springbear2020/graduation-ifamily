package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 11:22
 */
@Data
public class GenealogyDetailsBO implements Serializable {

    private static final long serialVersionUID = 8934848314133994593L;

    // 家族信息 =========================================================================================================

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

    // 成员概况信息 =====================================================================================================

    private Long total;

    private Long male;

    private Long female;

    private Long alive;

    private Long death;

    // 附加信息 =========================================================================================================

    /**
     * 是否用户默认家族：[0]否 [1]是
     */
    private Integer defaultGenealogy;

    /**
     * 家族创建者
     */
    private PeopleCardVO creator;

    /**
     * 家族管理员列表
     */
    private List<PeopleCardVO> admins;

}
