package cn.edu.whut.springbear.ifamily.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/05/02 08:52
 */
@Data
public class PhotoVO implements Serializable {

    private static final long serialVersionUID = -6736335917145212831L;

    private Long id;

    private String url;

}
