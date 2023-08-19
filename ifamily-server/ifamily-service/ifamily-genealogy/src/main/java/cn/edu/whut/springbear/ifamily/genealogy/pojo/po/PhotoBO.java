package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 09:53
 */
@Data
public class PhotoBO implements Serializable {

    private static final long serialVersionUID = -7344561698333762276L;

    private Date uploadTime;

    private PeopleCardVO uploader;

    private List<ImageVO> imgUrls;

    @Data
    public static class ImageVO {

        private Long id;

        private String url;
    }

}
