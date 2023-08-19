package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.RevisionBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.RevisionLogDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PageQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/07 11:49
 */
public interface RevisionLogService extends IService<RevisionLogDO> {

    /**
     * 保存家族人员操作日志
     *
     * @param type        操作类型：[1]新增 [2]删除 [3]编辑 [4]查看
     * @param peopleId    被操作者家族人员 ID
     * @param genealogyId 家族 ID
     * @return [true]保存成功
     */
    boolean create(Integer type, Long peopleId, Long genealogyId);

    /**
     * 查询家族修谱记录，按照操作日期、操作人员、操作类型三级结构进行分组。
     * - 每个操作日期下对应多条操作记录，每条记录对应一个操作用户
     * - 每个操作用户可以进行多种操作类型（CRUD），每种操作类型涉及到多个被操作人员
     * - date -> operator -> operationType -> operatedPeople，从前往后均为一对多关系
     *
     * @param pageQuery   分页查询条件
     * @param genealogyId 家族 ID
     * @return 家族修谱记录分页数据
     */
    List<RevisionBO> page(PageQuery pageQuery, Long genealogyId);

}
