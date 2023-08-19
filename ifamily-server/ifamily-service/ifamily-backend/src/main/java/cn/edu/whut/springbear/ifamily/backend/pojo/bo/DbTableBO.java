package cn.edu.whut.springbear.ifamily.backend.pojo.bo;

import cn.edu.whut.springbear.ifamily.backend.pojo.po.DbTableDO;
import lombok.Data;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/27 08:01
 */
@Data
public class DbTableBO {

    private Long id;

    private String name;

    private String description;

    private List<DbTableDO> tables;

}
