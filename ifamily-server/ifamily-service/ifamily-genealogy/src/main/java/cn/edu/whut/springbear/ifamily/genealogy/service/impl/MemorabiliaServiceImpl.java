package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.MemorabiliaMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.MemorabiliaDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemorabiliaQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.MemorabiliaVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemorabiliaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 12:39
 */
@Service
public class MemorabiliaServiceImpl extends ServiceImpl<MemorabiliaMapper, MemorabiliaDO> implements MemorabiliaService {

    @Override
    public boolean create(Long publisherUserId, Long genealogyId, MemorabiliaQuery memorabiliaQuery) {
        MemorabiliaDO memorabiliaDO = new MemorabiliaDO();
        BeanUtils.copyProperties(memorabiliaQuery, memorabiliaDO);
        memorabiliaDO.setPublisherUserId(publisherUserId);
        memorabiliaDO.setGenealogyId(genealogyId);
        Date date = new Date();
        memorabiliaDO.setModified(date);
        memorabiliaDO.setCreated(date);
        memorabiliaDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(memorabiliaDO);
    }

    @Override
    public List<MemorabiliaVO> page(PageQuery pageQuery, Long genealogyId) {
        // 查询家族大事，按发生年份逆序排序
        QueryWrapper<MemorabiliaDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).orderByDesc("occurred_year");
        Page<MemorabiliaDO> pageData = this.page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }

        List<MemorabiliaVO> resultList = new ArrayList<>();
        // DOList -> VOList
        for (MemorabiliaDO memorabiliaDO : pageData.getRecords()) {
            MemorabiliaVO memorabiliaVO = new MemorabiliaVO();
            BeanUtils.copyProperties(memorabiliaDO, memorabiliaVO);
            resultList.add(memorabiliaVO);
        }

        return resultList;
    }

}
