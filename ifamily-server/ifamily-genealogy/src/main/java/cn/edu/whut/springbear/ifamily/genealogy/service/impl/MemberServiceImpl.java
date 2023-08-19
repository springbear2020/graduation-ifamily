package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.exception.IllegalConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.GenderEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeNodeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemberQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private PeopleService peopleService;

    @Override
    public Object listGenerationMembers(MemberQuery query) {
        final Long genealogyId = query.getGenealogyId();
        // select * from people where genealogy_id = #{} and gender = #{} and name = #{}  and ISNULL(death_date)
        QueryWrapper<PeopleDO> wrapper = new QueryWrapper<>();
        wrapper.eq("genealogy_id", genealogyId);
        // 拼接额外查询条件：姓名 name、性别 gender [0]男 [1]女、生逝状态 [true]生 [false]逝
        if (StringUtils.hasLength(query.getName())) {
            wrapper.like("name", query.getName());
        }
        if (query.getGender() != null) {
            wrapper.eq("gender", query.getGender());
        }
        if (query.getAlive() != null) {
            if (query.getAlive()) {
                // 健在，逝世日期为 null
                wrapper.isNull("death_date");
            } else {
                wrapper.isNotNull("death_date");
            }
        }

        // 查询出所有符合条件的世代成员
        List<PeopleDO> peopleDOList = this.baseMapper.selectList(wrapper);
        if (peopleDOList == null || peopleDOList.isEmpty()) {
            return null;
        }

        // 将家族成员列表按照世代分组，而后封装为 Map：key = 世代，val = 世代成员列表
        Map<Integer, List<PeopleDO>> generationMemberMap = peopleDOList.stream()
                .collect(Collectors.groupingBy(PeopleDO::getGeneration));

        // 从 Map 中取出所有的键（世代）并升序排列
        List<Integer> generations = new ArrayList<>(generationMemberMap.keySet());
        generations = generations.stream().sorted().collect(Collectors.toList());

        // 将家族世代列表和每个世代下的成员数据封装返回
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("generations", generations);
        resultMap.put("members", generationMemberMap);
        return resultMap;
    }

    @Override
    public MemberTreeNodeBO memberTree(Long genealogyId) {
        // 获取家族的最小世代
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).select("min(generation) as generation");
        PeopleDO peopleDO = this.baseMapper.selectOne(queryWrapper);
        if (peopleDO == null || peopleDO.getGeneration() == null) {
            throw new IllegalConditionException("家族成员列表无数据");
        }

        // 一个家族只有一位共同祖先，根据最小世代查询人员信息，此即为家族的祖先
        final int oneCommonAncestor = 1;
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).eq("generation", peopleDO.getGeneration()).eq("gender", GenderEnum.MALE.getCode());
        List<PeopleDO> ancestors = this.baseMapper.selectList(queryWrapper);
        if (ancestors.size() > oneCommonAncestor) {
            throw new IllegalConditionException("家族只能存在一个共同祖先");
        }

        // 将家族祖先 DO 转换为 BO
        PeopleDO ancestorDO = ancestors.get(0);
        MemberTreeNodeBO ancestor = new MemberTreeNodeBO();
        BeanUtils.copyProperties(ancestorDO, ancestor);
        // 获取祖先的伴侣
        ancestor.setMates(this.listWifeList(ancestor.getId()));
        // 获取祖先的孩子列表
        this.getChildren(ancestor);

        return ancestor;
    }

    @Override
    public List<PeopleCardVO> listMembersByName(String name, Long genealogyId) {
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

    @Override
    public List<PeopleCardVO> listMemberByBatchUserIds(List<Long> userIds, Long genealogyId) {
        if (userIds == null || userIds.isEmpty()) {
            return null;
        }

        // 遍历所有的用户 ID，查询其家族人员信息
        List<PeopleCardVO> resultList = new ArrayList<>();
        userIds.forEach(userId -> {
            PeopleVO peopleVO = this.peopleService.getByUserGenealogyId(userId, genealogyId);
            if (peopleVO != null) {
                PeopleCardVO genealogyAdminVO = new PeopleCardVO();
                BeanUtils.copyProperties(peopleVO, genealogyAdminVO);
                resultList.add(genealogyAdminVO);
            }
        });

        return resultList;
    }

    /**
     * 查询丈夫的妻子列表
     */
    private List<MemberTreeNodeBO> listWifeList(Long husbandId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("husband_id", husbandId).orderByAsc("seniority");
        List<PeopleDO> wivesDO = this.baseMapper.selectList(queryWrapper);

        // 将所有的 DO 转换为 BO
        List<MemberTreeNodeBO> resultList = new ArrayList<>();
        wivesDO.forEach(item -> {
            MemberTreeNodeBO memberTreeNodeBO = new MemberTreeNodeBO();
            BeanUtils.copyProperties(item, memberTreeNodeBO);
            resultList.add(memberTreeNodeBO);
        });
        return resultList;
    }

    /**
     * 获取父亲的孩子列表
     */
    private void getChildren(MemberTreeNodeBO parent) {
        // 查询父亲下的所有孩子
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("father_id", parent.getId());
        List<PeopleDO> childList = this.baseMapper.selectList(queryWrapper);
        if (childList == null || childList.isEmpty()) {
            parent.setChildren(null);
            return;
        }

        List<MemberTreeNodeBO> children = new ArrayList<>();
        // 遍历所有孩子，依次查询其伴侣和孙子
        childList.forEach(item -> {
            // 创建孩子 BO
            MemberTreeNodeBO child = new MemberTreeNodeBO();
            BeanUtils.copyProperties(item, child);

            // 查询孩子的伴侣
            child.setMates(this.listWifeList(child.getId()));

            // 递归查询孩子的孙子列表
            getChildren(child);

            // 将孩子添加到孩子结果列表中
            children.add(child);
        });

        // 将孩子列表设置为父节点的子节点
        parent.setChildren(children);
    }

}
