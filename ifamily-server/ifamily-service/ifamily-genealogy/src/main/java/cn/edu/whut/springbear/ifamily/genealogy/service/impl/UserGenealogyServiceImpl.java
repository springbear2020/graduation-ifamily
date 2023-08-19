package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.constant.MessageConstants;
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
    public boolean createAndAssociate(Long userId, Long genealogyId) {
        // 将用户所有家族的是否默认家族状态设置为否
        this.setNoDefaultStatusOfGenealogies(userId);

        // 关联用户家族，并将家族设置为用户默认家族，将用户设置为家族管理员
        UserGenealogyDO userGenealogyDO = new UserGenealogyDO();
        userGenealogyDO.setGenealogyId(genealogyId);
        userGenealogyDO.setUserId(userId);
        userGenealogyDO.setDefaultGenealogy(AssertEnum.YES.getCode());
        userGenealogyDO.setGenealogyAdmin(AssertEnum.YES.getCode());
        Date date = new Date();
        userGenealogyDO.setCreated(date);
        userGenealogyDO.setModified(date);
        userGenealogyDO.setDeleted(AssertEnum.NO.getCode());
        return this.save(userGenealogyDO);
    }

    @Override
    public List<UserGenealogyDO> listGenealogiesOfUser(Long userId) {
        QueryWrapper<UserGenealogyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }

    @Override
    public List<Long> listAdminIdsOfGenealogy(Long genealogyId) {
        QueryWrapper<UserGenealogyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId);
        List<UserGenealogyDO> genealogyAdminList = this.list(queryWrapper);
        return genealogyAdminList.stream()
                .map(UserGenealogyDO::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean setDefault(Long userId, Long genealogyId) {
        // 验证 genealogyId 的家族是否隶属于 userId 的用户
        boolean hasThisGenealogy = this.listGenealogiesOfUser(userId).stream()
                .anyMatch(item -> item.getGenealogyId().equals(genealogyId));
        if (!hasThisGenealogy) {
            throw new IncorrectConditionException(MessageConstants.GENEALOGY_NOT_EXISTS);
        }

        // 更新用户所有家族的默认家族状态为否
        this.setNoDefaultStatusOfGenealogies(userId);

        // 设置当前家族为用户的默认家族
        UpdateWrapper<UserGenealogyDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("genealogy_id", genealogyId).eq("user_id", userId);
        updateWrapper.set("is_default_genealogy", AssertEnum.YES.getCode());
        return this.update(updateWrapper);
    }

    @Override
    public UserGenealogyDO getDefault(Long userId) {
        QueryWrapper<UserGenealogyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("is_default_genealogy", AssertEnum.YES.getCode());
        UserGenealogyDO defaultGenealogy = this.getOne(queryWrapper);
        if (defaultGenealogy == null) {
            throw new IncorrectConditionException(MessageConstants.GENEALOGY_NOT_EXISTS);
        }
        return this.getOne(queryWrapper);
    }

    /**
     * 将用户所有家族的默认家族状态修改为否
     *
     * @param userId 用户 ID
     */
    private void setNoDefaultStatusOfGenealogies(Long userId) {
        UpdateWrapper<UserGenealogyDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_default_genealogy", AssertEnum.NO.getCode()).eq("user_id", userId);
        this.update(updateWrapper);
    }

}
