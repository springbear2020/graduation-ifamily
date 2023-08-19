package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.UserGenealogyMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:34
 */
@Service
public class UserGenealogyServiceImpl extends ServiceImpl<UserGenealogyMapper, UserGenealogyDO> implements UserGenealogyService {

    @Override
    public boolean updateDefault(Long userId, Integer status) {
        UpdateWrapper<UserGenealogyDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("default_genealogy", status).eq("user_id", userId);
        this.baseMapper.update(null, updateWrapper);
        return true;
    }

    @Override
    public boolean createDefault(Long userId, Long genealogyId) {
        UserGenealogyDO userGenealogyDO = new UserGenealogyDO();
        userGenealogyDO.setGenealogyId(genealogyId);
        userGenealogyDO.setUserId(userId);
        userGenealogyDO.setDefaultGenealogy(AssertEnum.YES.getCode());
        userGenealogyDO.setGenealogyAdmin(AssertEnum.YES.getCode());
        Date date = new Date();
        userGenealogyDO.setCreated(date);
        userGenealogyDO.setModified(date);
        userGenealogyDO.setDeleted(AssertEnum.NO.getCode());
        return this.baseMapper.insert(userGenealogyDO) == 1;
    }

    @Override
    public List<UserGenealogyDO> listGenealogiesOfUser(Long userId) {
        QueryWrapper<UserGenealogyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean setDefault(Long userId, Long genealogyId) {
        // 将用户关联的所有家族的默认状态修改为否
        this.updateDefault(userId, AssertEnum.NO.getCode());
        // 修改用户关联的 ID 为 genealogyId 的默认状态为是
        UpdateWrapper<UserGenealogyDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId).eq("user_id", userId);
        updateWrapper.set("default_genealogy", AssertEnum.YES.getCode());
        return this.baseMapper.update(null, updateWrapper) == 1;
    }

    @Override
    public UserGenealogyDO getDefault(Long userId) {
        QueryWrapper<UserGenealogyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("default_genealogy", AssertEnum.YES.getCode());
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Long> listAdminIdsOfGenealogy(Long genealogyId) {
        QueryWrapper<UserGenealogyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId);
        List<UserGenealogyDO> genealogyAdminList = this.baseMapper.selectList(queryWrapper);
        return genealogyAdminList.stream()
                .map(UserGenealogyDO::getUserId)
                .collect(Collectors.toList());
    }

}
