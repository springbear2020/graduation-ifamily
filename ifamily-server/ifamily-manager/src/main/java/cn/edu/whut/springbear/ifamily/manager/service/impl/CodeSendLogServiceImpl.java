package cn.edu.whut.springbear.ifamily.manager.service.impl;

import cn.edu.whut.springbear.ifamily.manager.mapper.CodeSendLogMapper;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeSendLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.CodeSendLogService;
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
public class CodeSendLogServiceImpl extends ServiceImpl<CodeSendLogMapper, CodeSendLogDO> implements CodeSendLogService {

    @Override
    public List<CodeSendLogDO> listOnSpecifiedDateOfReceiver(String receiver, Date date) {
        QueryWrapper<CodeSendLogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver", receiver).eq("DATE(created)", date);
        return this.baseMapper.selectList(queryWrapper);
    }

}