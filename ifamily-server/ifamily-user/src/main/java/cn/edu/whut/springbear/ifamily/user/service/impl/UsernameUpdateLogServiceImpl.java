package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.user.mapper.UsernameUpdateLogMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UsernameUpdateLogDO;
import cn.edu.whut.springbear.ifamily.user.service.UsernameUpdateLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:34
 */
@Service
public class UsernameUpdateLogServiceImpl extends ServiceImpl<UsernameUpdateLogMapper, UsernameUpdateLogDO> implements UsernameUpdateLogService {

    @Override
    public UsernameUpdateLogDO getLatestOfUser(Long userId) {
        QueryWrapper<UsernameUpdateLogDO> queryWrapper = new QueryWrapper<>();
        // 根据 ID 查询用户最新的用户名修改记录，而后根据创建时间逆序排序
        queryWrapper.eq("user_id", userId).orderByDesc("created");
        List<UsernameUpdateLogDO> list = this.baseMapper.selectList(queryWrapper);
        if (list == null || list.isEmpty()) {
            return null;
        }
        // 取出最新一条修改记录返回
        return list.get(0);
    }

    @Override
    public boolean create(String oldUsername, String newUsername, Long userId) {
        Date date = new Date();
        UsernameUpdateLogDO log = new UsernameUpdateLogDO(null, oldUsername, newUsername, date, date, DeleteStatusEnum.UNDELETED.getCode(), userId);
        return this.baseMapper.insert(log) == 1;
    }

}
