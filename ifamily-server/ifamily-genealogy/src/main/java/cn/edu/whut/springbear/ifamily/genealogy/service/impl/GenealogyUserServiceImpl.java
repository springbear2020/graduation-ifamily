package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.DefaultStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyUserMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:34
 */
@Service
public class GenealogyUserServiceImpl extends ServiceImpl<GenealogyUserMapper, GenealogyUserDO> implements GenealogyUserService {

    @Override
    public boolean updateGenealogiesStatusOfUser(Long userId, Integer status) {
        UpdateWrapper<GenealogyUserDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("default_genealogy", status).eq("user_id", userId);
        this.baseMapper.update(null, updateWrapper);
        return true;
    }

    @Override
    public boolean createUserDefaultGenealogy(Long userId, Long genealogyId) {
        GenealogyUserDO genealogyUserDO = new GenealogyUserDO();
        genealogyUserDO.setGenealogyId(genealogyId);
        genealogyUserDO.setUserId(userId);
        genealogyUserDO.setDefaultGenealogy(DefaultStatusEnum.YES.getCode());
        Date date = new Date();
        genealogyUserDO.setCreated(date);
        genealogyUserDO.setModified(date);
        genealogyUserDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        return this.baseMapper.insert(genealogyUserDO) == 1;
    }

    @Override
    public List<GenealogyUserDO> listGenealogiesOfUser(Long userId) {
        QueryWrapper<GenealogyUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean setDefaultGenealogyForUser(Long userId, Long genealogyId) {
        // 将用户关联的所有家族的默认状态修改为否
        this.updateGenealogiesStatusOfUser(userId, DefaultStatusEnum.NO.getCode());
        // 修改用户关联的 ID 为 genealogyId 的默认状态为是
        UpdateWrapper<GenealogyUserDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId).eq("user_id", userId);
        updateWrapper.set("default_genealogy", DefaultStatusEnum.YES.getCode());
        return this.baseMapper.update(null, updateWrapper) == 1;
    }

    @Override
    public GenealogyUserDO getDefaultGenealogyOfUser(Long userId) {
        QueryWrapper<GenealogyUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("default_genealogy", DefaultStatusEnum.YES.getCode());
        return this.baseMapper.selectOne(queryWrapper);
    }

}
