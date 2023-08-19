package cn.edu.whut.springbear.ifamily.social.pojo.bo;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 16:22
 */
@Data
public class MomentCommentBO implements Serializable {

    private static final long serialVersionUID = 8032496539513006135L;

    private Long id;

    private String content;

    private CommonUserVO sourceUser;

    private CommonUserVO targetUser;

}
