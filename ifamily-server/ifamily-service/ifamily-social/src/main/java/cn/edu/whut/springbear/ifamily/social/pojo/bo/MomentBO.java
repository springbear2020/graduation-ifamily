package cn.edu.whut.springbear.ifamily.social.pojo.bo;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.PhotoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 22:36
 */
@Data
public class MomentBO implements Serializable {

    private static final long serialVersionUID = 3696001525152666666L;

    private Long id;

    private String content;

    private Integer whoCanSee;

    private List<PhotoVO> imgUrls;

}
