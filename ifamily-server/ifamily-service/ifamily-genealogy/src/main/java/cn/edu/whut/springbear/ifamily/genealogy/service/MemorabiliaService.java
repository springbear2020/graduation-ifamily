package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.MemorabiliaDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemorabiliaQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.MemorabiliaVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 12:39
 */
public interface MemorabiliaService extends IService<MemorabiliaDO> {

    /**
     * 保存家族大事
     *
     * @param publisherUserId  大事发布者用户 ID
     * @param genealogyId      家族 ID
     * @param memorabiliaQuery 家族大事内容
     * @return [true]保存成功
     */
    boolean create(Long publisherUserId, Long genealogyId, MemorabiliaQuery memorabiliaQuery);

    /**
     * 查询家族大事分页数据
     *
     * @param pageQuery   分页条件
     * @param genealogyId 家族 ID
     * @return 家族大事数据
     */
    List<MemorabiliaVO> page(PageQuery pageQuery, Long genealogyId);

}
