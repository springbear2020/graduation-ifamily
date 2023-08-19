package cn.edu.whut.springbear.ifamily.genealogy.pojo.bo;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 家族修谱记录：
 * - 每个操作日期下对应多条操作记录，每条记录对应一个操作用户
 * - 每个操作用户可以进行多种操作类型（CRUD），每种操作类型涉及到多个被操作人员
 * - date -> operator -> operationType -> operatedPeople，从前往后均为一对多关系
 *
 * @author Spring-_-Bear
 * @since 23/04/08 07:13
 */
@Data
public class RevisionBO implements Serializable {

    private static final long serialVersionUID = -8023686915170115293L;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operationDate;

    /**
     * 操作记录
     */
    private List<OperationLog> dateLogs;

    @Data
    public static class OperationLog {

        /**
         * 操作者
         */
        private PeopleCardVO operator;

        /**
         * 操作类型：[1]新增 [2]删除 [3]编辑 [4]查看
         */
        private Integer operationType;

        /**
         * 被操作人员列表
         */
        private List<String> operatedPeopleList;

    }

}
