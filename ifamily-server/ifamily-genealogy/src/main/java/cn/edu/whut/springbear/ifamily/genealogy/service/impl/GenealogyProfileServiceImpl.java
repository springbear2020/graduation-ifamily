package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.GenderEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyProfileMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyProfileDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyProfileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:03
 */
@Service
public class GenealogyProfileServiceImpl extends ServiceImpl<GenealogyProfileMapper, GenealogyProfileDO> implements GenealogyProfileService {

    @Override
    public boolean create(Long genealogyId) {
        GenealogyProfileDO overviewDO = new GenealogyProfileDO();
        final long zero = 0;
        overviewDO.setTotal(zero);
        overviewDO.setMale(zero);
        overviewDO.setFemale(zero);
        overviewDO.setAlive(zero);
        overviewDO.setDeath(zero);
        Date date = new Date();
        overviewDO.setCreated(date);
        overviewDO.setModified(date);
        overviewDO.setDeleted(AssertEnum.NO.getCode());
        overviewDO.setGenealogyId(genealogyId);
        return this.baseMapper.insert(overviewDO) == 1;
    }

    @Override
    public GenealogyProfileDO getByGenealogyId(Long gid) {
        QueryWrapper<GenealogyProfileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", gid);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer increaseOne(Long genealogyId, Integer gender, Date deathDate) {
        UpdateWrapper<GenealogyProfileDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId).setSql("total = total + 1");
        // 根据性别将男性或女性人数 +1
        if (GenderEnum.MALE.getCode().equals(gender)) {
            updateWrapper.setSql("male = male + 1");
        } else {
            updateWrapper.setSql("female = female + 1");
        }
        // 根据生逝状态将健在或已逝人数 +1
        if (deathDate == null) {
            updateWrapper.setSql("alive = alive + 1");
        } else {
            updateWrapper.setSql("death = death + 1");
        }
        return this.baseMapper.update(null, updateWrapper);
    }

    @Override
    public boolean changeByGender(Integer newGender, Integer oldGender, Long genealogyId) {
        if (newGender == null || oldGender == null || newGender.equals(oldGender)) {
            return false;
        }

        UpdateWrapper<GenealogyProfileDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId);
        if (GenderEnum.MALE.getCode().equals(newGender)) {
            // 家族男性人数 +1，女性人数 -1
            updateWrapper.setSql("male = male + 1, female = female - 1");
        } else {
            // 家族女性人数 +1, 男性人数 -1
            updateWrapper.setSql("male = male - 1, female = female + 1");
        }
        return this.baseMapper.update(null, updateWrapper) == 1;
    }

    @Override
    public boolean changeByDeathDate(Date newDeathDate, Date oldDeathDate, Long genealogyId) {
        // 已逝新、旧日期均为 null，当前人员健在，不影响家族生逝人数
        if (newDeathDate == null && oldDeathDate == null) {
            return false;
        }
        // 已逝日期新、旧均不为 null，当前人员已逝，仅仅修改日期值，不影响家族生逝人数
        if (newDeathDate != null && oldDeathDate != null) {
            return false;
        }

        UpdateWrapper<GenealogyProfileDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId);
        if (newDeathDate != null) {
            // 家族已逝人数 +1，健在人数 -1
            updateWrapper.setSql("death = death + 1, alive = alive - 1");
        } else {
            // 家族已逝人数 -1，健在人数 +1
            updateWrapper.setSql("death = death - 1, alive = alive + 1");
        }

        return this.baseMapper.update(null, updateWrapper) == 1;
    }

    @Override
    public Integer decreaseOne(Long genealogyId, Integer gender, Date deathDate) {
        UpdateWrapper<GenealogyProfileDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId).setSql("total = total - 1");
        // 根据性别将男性或女性人数 -1
        if (GenderEnum.MALE.getCode().equals(gender)) {
            updateWrapper.setSql("male = male - 1");
        } else {
            updateWrapper.setSql("female = female - 1");
        }
        // 根据生逝状态将健在或已逝人数 -1
        if (deathDate == null) {
            updateWrapper.setSql("alive = alive - 1");
        } else {
            updateWrapper.setSql("death = death - 1");
        }
        return this.baseMapper.update(null, updateWrapper);
    }

}
