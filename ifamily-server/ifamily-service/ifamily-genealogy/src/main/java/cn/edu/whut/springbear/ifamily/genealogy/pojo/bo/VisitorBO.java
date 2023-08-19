package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/07 21:58
 */
@Data
public class VisitorBO implements Serializable {

    private static final long serialVersionUID = -264309206429592343L;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitedDate;

    private List<PeopleCardVO> visitors;

}
