package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/16 16:29
 */
@Data
public class NoticeBO implements Serializable {

    private static final long serialVersionUID = -2721017045375482462L;

    private PeopleCardVO announcer;

    private Long id;

    private String content;

    private Date created;

}
