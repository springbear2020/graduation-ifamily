package cn.edu.whut.springbear.ifamily.genealogy.mapper;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:01
 */
@Repository
public interface PeopleMapper extends BaseMapper<PeopleDO> {

    /**
     * FIXME 家族所有成员（含已逻辑删除成员）世代增加 1
     *
     * @param genealogyId 家族 ID
     * @return 影响的行数
     */
    @Update("update people set generation = generation + 1 where genealogy_id = #{genealogyId}")
    int generationIncreaseOne(@Param("genealogyId") Long genealogyId);

}
