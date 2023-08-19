package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.exception.IllegalConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyMemberTreeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyMemberCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:02
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, PeopleDO> implements PeopleService {

    @Autowired
    private GenealogyUserService genealogyUserService;

    @Override
    public Object listGenerationMembersOfGenealogy(Long genealogyId) {
        // 查询家族下的所有世代
        List<Integer> generationNumberList = this.listGenerationNumsOfGenealogy(genealogyId);
        if (generationNumberList == null || generationNumberList.isEmpty()) {
            return null;
        }

        // 遍历家族下所有的世代，依次查询每个世代下的家族成员，而后封装为 Map：key = 世代，val = 世代成员列表
        Map<Integer, List<GenealogyMemberCardVO>> generationPeopleMap = new HashMap<>();
        generationNumberList.forEach(generation -> {
            List<PeopleDO> peopleOfGenerationList = this.listPeopleOfGeneration(genealogyId, generation);
            // 遍历 DO 列表，将每一个 DO 转换为 VO
            List<GenealogyMemberCardVO> peopleList = new ArrayList<>();
            peopleOfGenerationList.forEach(item -> {
                GenealogyMemberCardVO genealogyMemberCardVO = new GenealogyMemberCardVO();
                BeanUtils.copyProperties(item, genealogyMemberCardVO);
                peopleList.add(genealogyMemberCardVO);
            });
            generationPeopleMap.put(generation, peopleList);
        });

        // 将家族世代列表和每个世代下的成员数据封装返回
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("generations", generationNumberList);
        resultMap.put("generationPeopleList", generationPeopleMap);
        return resultMap;
    }

    @Override
    public List<Integer> listGenerationNumsOfGenealogy(Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("generation").eq("genealogy_id", genealogyId).groupBy("generation").orderByAsc("generation");
        List<PeopleDO> peopleDOList = this.baseMapper.selectList(queryWrapper);
        return peopleDOList.stream()
                .map(PeopleDO::getGeneration)
                .collect(Collectors.toList());
    }

    @Override
    public List<PeopleDO> listPeopleOfGeneration(Long genealogyId, Integer generation) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).eq("generation", generation);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public GenealogyMemberTreeBO listMemberTree(Long genealogyId) {
        // 查询家族下的所有世代
        List<Integer> generationNumberList = this.listGenerationNumsOfGenealogy(genealogyId);
        if (generationNumberList == null || generationNumberList.isEmpty()) {
            return null;
        }

        // 家族只有一个共同祖先：根据最小代数查询人员信息，此即为家族的祖先
        final int oneCommonAncestor = 1;
        int minGeneration = generationNumberList.get(0);
        List<PeopleDO> ancestors = this.listPeopleOfGeneration(genealogyId, minGeneration);
        if (ancestors.size() > oneCommonAncestor) {
            throw new IllegalConditionException("家族只能存在一个共同祖先");
        }

        // 将家族祖先 DO 转换为 BO
        PeopleDO ancestorDO = ancestors.get(0);
        GenealogyMemberTreeBO ancestor = new GenealogyMemberTreeBO();
        BeanUtils.copyProperties(ancestorDO, ancestor);
        // 获取祖先的伴侣
        ancestor.setMate(this.listWivesOfPeople(ancestor.getId()));
        // 获取祖先的孩子列表
        this.getChildren(ancestor);

        return ancestor;
    }

    @Override
    public PeopleVO getByUserGenealogyId(Long userId, Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("genealogy_id", genealogyId);
        PeopleDO peopleDO = this.baseMapper.selectOne(queryWrapper);
        PeopleVO peopleVO = new PeopleVO();
        if (peopleDO != null) {
            BeanUtils.copyProperties(peopleDO, peopleVO);
        }
        return peopleVO;
    }

    /**
     * 获取父亲的孩子
     */
    private void getChildren(GenealogyMemberTreeBO parent) {
        // 查询父亲下的所有孩子
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("father_id", parent.getId());
        List<PeopleDO> childList = this.baseMapper.selectList(queryWrapper);
        if (childList == null || childList.isEmpty()) {
            parent.setChildren(null);
            return;
        }

        List<GenealogyMemberTreeBO> children = new ArrayList<>();
        // 遍历所有孩子，依次查询其伴侣和孙子
        childList.forEach(item -> {
            // 创建孩子 BO
            GenealogyMemberTreeBO child = new GenealogyMemberTreeBO();
            BeanUtils.copyProperties(item, child);

            // 查询孩子的伴侣
            child.setMate(this.listWivesOfPeople(child.getId()));

            // 递归查询孩子的孙子列表
            getChildren(child);

            // 将孩子添加到孩子结果列表中
            children.add(child);
        });

        // 将孩子列表设置为父节点的子节点
        parent.setChildren(children);
    }

    /**
     * 获取丈夫的所有妻子
     */
    private List<GenealogyMemberTreeBO> listWivesOfPeople(Long husbandId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("husband_id", husbandId);
        List<PeopleDO> wives = this.baseMapper.selectList(queryWrapper);
        if (wives == null || wives.isEmpty()) {
            return null;
        }

        // 将所有的 DO 转换为 BO
        List<GenealogyMemberTreeBO> resultList = new ArrayList<>();
        wives.forEach(item -> {
            GenealogyMemberTreeBO genealogyMemberTreeBO = new GenealogyMemberTreeBO();
            BeanUtils.copyProperties(item, genealogyMemberTreeBO);
            resultList.add(genealogyMemberTreeBO);
        });
        return resultList;
    }

}
