package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyAdminMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyAdminDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyAdminVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyAdminService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 19:25
 */
@Service
public class GenealogyAdminServiceImpl extends ServiceImpl<GenealogyAdminMapper, GenealogyAdminDO> implements GenealogyAdminService {

    @Autowired
    private PeopleService peopleService;

    @Override
    public List<GenealogyAdminVO> listAdminsOfGenealogy(Long genealogyId) {
        // 查询出所有的家族管理员 ID 集合
        List<Long> adminUserIds = this.listAdminUserIdsOfGenealogy(genealogyId);
        if (adminUserIds == null || adminUserIds.isEmpty()) {
            return null;
        }

        // 遍历所有的管理员用户 ID，查询其家族用户信息
        List<GenealogyAdminVO> resultList = new ArrayList<>();
        adminUserIds.forEach(userId -> {
            PeopleVO peopleVO = peopleService.getByUserGenealogyId(userId, genealogyId);
            if (peopleVO != null) {
                GenealogyAdminVO genealogyAdminVO = new GenealogyAdminVO();
                BeanUtils.copyProperties(peopleVO, genealogyAdminVO);
                resultList.add(genealogyAdminVO);
            }
        });
        return resultList;
    }

    @Override
    public List<Long> listAdminUserIdsOfGenealogy(Long genealogyId) {
        QueryWrapper<GenealogyAdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId);
        List<GenealogyAdminDO> genealogyAdminList = this.baseMapper.selectList(queryWrapper);
        return genealogyAdminList.stream()
                .map(GenealogyAdminDO::getUserId)
                .collect(Collectors.toList());
    }

}
