package cn.edu.whut.springbear.ifamily.user.mapper;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * FIXME 根据列名查询单元格值，含已逻辑删除的记录
     *
     * @param column 列名
     * @param value  列值
     * @return 单元格值
     */
    @Select("select ${column} from user where ${column} = #{value}")
    Object getCellValueByColumn(@Param("column") String column, @Param("value") String value);

}
