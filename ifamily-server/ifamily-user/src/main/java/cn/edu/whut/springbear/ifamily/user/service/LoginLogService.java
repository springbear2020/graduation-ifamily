package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.LoginLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.LoginLogVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface LoginLogService extends IService<LoginLogDO> {

    /**
     * 新增用户登录记录
     */
    boolean create(Long userId);

    /**
     * 查询用户登录记录分页数据
     */
    List<LoginLogVO> page(PageQuery pageQuery, Long userId);

}
