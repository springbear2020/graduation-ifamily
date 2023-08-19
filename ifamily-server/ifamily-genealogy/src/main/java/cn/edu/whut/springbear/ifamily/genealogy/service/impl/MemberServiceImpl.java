package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.exception.IllegalConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/03 17:49
 */
@Service
public class MemberServiceImpl extends ServiceImpl<PeopleMapper, PeopleDO> implements MemberService {

    @Override
    public Object listGenerationMembersOfGenealogy(Long genealogyId) {
        // 查询家族下的所有世代
        List<Integer> generationNumberList = this.listGenerationNumsOfGenealogy(genealogyId);
        if (generationNumberList == null || generationNumberList.isEmpty()) {
            return null;
        }

        // 遍历家族下所有的世代，依次查询每个世代下的家族成员，而后封装为 Map：key = 世代，val = 世代成员列表
        Map<Integer, List<PeopleCardVO>> generationPeopleMap = new HashMap<>();
        generationNumberList.forEach(generation -> {
            List<PeopleDO> peopleOfGenerationList = this.listPeopleOfGeneration(genealogyId, generation);
            // 遍历 DO 列表，将每一个 DO 转换为 VO
            List<PeopleCardVO> peopleList = new ArrayList<>();
            peopleOfGenerationList.forEach(item -> {
                PeopleCardVO peopleCardVO = new PeopleCardVO();
                BeanUtils.copyProperties(item, peopleCardVO);
                peopleList.add(peopleCardVO);
            });
            generationPeopleMap.put(generation, peopleList);
        });

        // 将家族世代列表和每个世代下的成员数据封装返回
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("generations", generationNumberList);
        resultMap.put("memberList", generationPeopleMap);
        return resultMap;
    }

    @Override
    public MemberTreeBO listGenealogyMemberTree(Long genealogyId) {
        // 查询家族下的所有世代
        List<Integer> generationNumberList = this.listGenerationNumsOfGenealogy(genealogyId);
        if (generationNumberList == null || generationNumberList.isEmpty()) {
            return null;
        }

        // 家族只有一对共同祖先：根据最小代数查询人员信息，此即为家族的祖先
        final int oneCommonAncestor = 2;
        int minGeneration = generationNumberList.get(0);
        List<PeopleDO> ancestors = this.listPeopleOfGeneration(genealogyId, minGeneration);
        if (ancestors.size() > oneCommonAncestor) {
            throw new IllegalConditionException("家族只能存在一个共同祖先");
        }

        // 将家族祖先 DO 转换为 BO
        PeopleDO ancestorDO = ancestors.get(0);
        MemberTreeBO ancestor = new MemberTreeBO();
        BeanUtils.copyProperties(ancestorDO, ancestor);
        // 获取祖先的伴侣
        ancestor.setMate(this.listWifeList(ancestor.getId()));
        // 获取祖先的孩子列表
        this.getChildren(ancestor);

        return ancestor;
    }

    @Override
    public List<PeopleCardVO> listPeopleByNameOfGenealogy(String name, Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).like("name", name);
        List<PeopleDO> peopleDOList = this.baseMapper.selectList(queryWrapper);

        // 将所有 DO 转换为 VO
        List<PeopleCardVO> resultList = new ArrayList<>();
        peopleDOList.forEach(item -> {
            PeopleCardVO peopleCardVO = new PeopleCardVO();
            BeanUtils.copyProperties(item, peopleCardVO);
            resultList.add(peopleCardVO);
        });
        return resultList;
    }

    /**
     * 查询家族世代下的所有人员
     */
    public List<PeopleDO> listPeopleOfGeneration(Long genealogyId, Integer generation) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).eq("generation", generation);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询家族下的世代集合，升序排列
     */
    private List<Integer> listGenerationNumsOfGenealogy(Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("generation").eq("genealogy_id", genealogyId).groupBy("generation").orderByAsc("generation");
        List<PeopleDO> peopleDOList = this.baseMapper.selectList(queryWrapper);
        return peopleDOList.stream()
                .map(PeopleDO::getGeneration)
                .collect(Collectors.toList());
    }

    /**
     * 查询丈夫的妻子列表
     */
    private List<MemberTreeBO> listWifeList(Long husbandId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("husband_id", husbandId).orderByAsc("seniority");
        List<PeopleDO> wivesDO = this.baseMapper.selectList(queryWrapper);

        // 将所有的 DO 转换为 BO
        List<MemberTreeBO> resultList = new ArrayList<>();
        wivesDO.forEach(item -> {
            MemberTreeBO memberTreeBO = new MemberTreeBO();
            BeanUtils.copyProperties(item, memberTreeBO);
            resultList.add(memberTreeBO);
        });
        return resultList;
    }

    /**
     * 获取父亲的孩子列表
     */
    private void getChildren(MemberTreeBO parent) {
        // 查询父亲下的所有孩子
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("father_id", parent.getId());
        List<PeopleDO> childList = this.baseMapper.selectList(queryWrapper);
        if (childList == null || childList.isEmpty()) {
            parent.setChildren(null);
            return;
        }

        List<MemberTreeBO> children = new ArrayList<>();
        // 遍历所有孩子，依次查询其伴侣和孙子
        childList.forEach(item -> {
            // 创建孩子 BO
            MemberTreeBO child = new MemberTreeBO();
            BeanUtils.copyProperties(item, child);

            // 查询孩子的伴侣
            child.setMate(this.listWifeList(child.getId()));

            // 递归查询孩子的孙子列表
            getChildren(child);

            // 将孩子添加到孩子结果列表中
            children.add(child);
        });

        // 将孩子列表设置为父节点的子节点
        parent.setChildren(children);
    }

}
