package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UserLoginLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.LoginLogVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserLoginLogService extends IService<UserLoginLogDO> {

    /**
     * 新增用户登录记录
     */
    boolean create(Long userId);

    /**
     * 查询用户登录记录分页数据，若 userId 为 null 则查询当前请求用户
     */
    List<LoginLogVO> page(PageQuery pageQuery, Long userId);

}
