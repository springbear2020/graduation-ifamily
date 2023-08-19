package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.user.mapper.UsernameLogMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UsernameLogDO;
import cn.edu.whut.springbear.ifamily.user.service.UsernameLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:34
 */
@Service
public class UsernameLogServiceImpl extends ServiceImpl<UsernameLogMapper, UsernameLogDO> implements UsernameLogService {

    @Override
    public UsernameLogDO getLatest(Long userId) {
        QueryWrapper<UsernameLogDO> queryWrapper = new QueryWrapper<>();
        // 根据 ID 查询用户用户名修改记录，而后根据创建时间逆序排序，取第一条修改记录返回
        queryWrapper.eq("user_id", userId).orderByDesc("created").last("limit 1");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean create(String oldUsername, String newUsername, Long userId) {
        UsernameLogDO usernameUpdateLog = new UsernameLogDO();
        usernameUpdateLog.setOldUsername(oldUsername);
        usernameUpdateLog.setNewUsername(newUsername);
        Date date = new Date();
        usernameUpdateLog.setCreated(date);
        usernameUpdateLog.setModified(date);
        usernameUpdateLog.setDeleted(AssertEnum.NO.getCode());
        usernameUpdateLog.setUserId(userId);
        return this.save(usernameUpdateLog);
    }

}
