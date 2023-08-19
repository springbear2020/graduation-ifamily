package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/02 10:25
 */
@Data
public class PeopleDetailsBO implements Serializable {

    private static final long serialVersionUID = 3973293200814258269L;

    private PeopleVO me;

    private PeopleCardVO father;

    private PeopleCardVO mother;

    private List<PeopleCardVO> mates;

    private List<PeopleCardVO> children;

    private List<PeopleCardVO> compatriots;

}