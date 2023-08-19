package cn.edu.whut.springbear.ifamily.genealogy.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 12:55
 */
@Data
public class MemorabiliaVO implements Serializable {

    private static final long serialVersionUID = 7748154953025848417L;

    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy")
    private Date occurredYear;

    private String cover;

    private String content;

}
