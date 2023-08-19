package cn.edu.whut.springbear.ifamily.social.pojo.bo;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.PhotoVO;
import cn.edu.whut.springbear.ifamily.social.pojo.vo.MomentLikeUserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 21:15
 */
@Data
public class MomentDetailsBO implements Serializable {

    private static final long serialVersionUID = 3696001525152666666L;

    /**
     * 动态 ID
     */
    private Long id;
    /**
     * 动态内容
     */
    private String content;
    /**
     * 动态权限
     */
    private Integer whoCanSee;
    /**
     * 动态实际发布时间
     */
    private Date scheduled;
    /**
     * 动态图片列表
     */
    private List<PhotoVO> imgUrls;
    /**
     * 动态点赞用户列表
     */
    private List<MomentLikeUserVO> likeList;
    /**
     * 动态评论列表
     */
    private List<MomentCommentBO> commentList;
    /**
     * 动态发布者用户信息
     */
    private CommonUserVO publisher;
    /**
     * 当前登录用户是否已点赞当前动态
     */
    private Boolean currentUserLiked;

}
