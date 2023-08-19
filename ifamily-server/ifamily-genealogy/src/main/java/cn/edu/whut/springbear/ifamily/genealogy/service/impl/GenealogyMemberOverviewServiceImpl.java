package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyMemberOverviewMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyMemberOverviewDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyMemberOverviewService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:03
 */
@Service
public class GenealogyMemberOverviewServiceImpl extends ServiceImpl<GenealogyMemberOverviewMapper, GenealogyMemberOverviewDO> implements GenealogyMemberOverviewService {

    @Override
    public boolean createDefault(Long genealogyId) {
        GenealogyMemberOverviewDO overviewDO = new GenealogyMemberOverviewDO();
        final long zero = 0;
        overviewDO.setTotal(zero);
        overviewDO.setMale(zero);
        overviewDO.setFemale(zero);
        overviewDO.setAlive(zero);
        overviewDO.setDeath(zero);
        Date date = new Date();
        overviewDO.setCreated(date);
        overviewDO.setModified(date);
        overviewDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        overviewDO.setGenealogyId(genealogyId);
        return this.baseMapper.insert(overviewDO) == 1;
    }

    @Override
    public GenealogyMemberOverviewDO getByGenealogyId(Long gid) {
        QueryWrapper<GenealogyMemberOverviewDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", gid);
        return this.baseMapper.selectOne(queryWrapper);
    }

}
