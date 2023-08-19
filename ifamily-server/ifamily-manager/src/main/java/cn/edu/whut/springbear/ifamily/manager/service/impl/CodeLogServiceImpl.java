package cn.edu.whut.springbear.ifamily.manager.service.impl;

import cn.edu.whut.springbear.ifamily.manager.mapper.CodeLogMapper;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.CodeLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-23
 */
@Service
public class CodeLogServiceImpl extends ServiceImpl<CodeLogMapper, CodeLogDO> implements CodeLogService {

    @Override
    public List<CodeLogDO> listByDateAndReceiver(String receiver, Date date) {
        QueryWrapper<CodeLogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver", receiver).eq("DATE(`created`)", date);
        return this.baseMapper.selectList(queryWrapper);
    }

}