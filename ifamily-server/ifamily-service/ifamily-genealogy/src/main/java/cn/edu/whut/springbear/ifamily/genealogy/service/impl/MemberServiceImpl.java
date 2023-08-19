package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.GenderEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeNodeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemberQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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

    @Override
    public List<PeopleDO> listMemberInBatchUserIds(List<Long> userIds, Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).in("user_id", userIds);
        return this.list(queryWrapper);
    }

    @Override
    public MemberTreeNodeBO memberTree(Long genealogyId) {
        // 获取家族的最小世代
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).select("MIN(`generation`) as generation");
        PeopleDO peopleDO = this.getOne(queryWrapper);
        if (peopleDO == null || peopleDO.getGeneration() == null) {
            throw new IncorrectConditionException("家族祖先人员信息不存在");
        }

        // 一个家族只有一位共同祖先，根据最小世代查询人员信息，此即为家族的祖先
        final int oneCommonAncestor = 1;
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).eq("generation", peopleDO.getGeneration()).eq("gender", GenderEnum.MALE.getCode());
        List<PeopleDO> ancestors = this.list(queryWrapper);
        if (ancestors.size() > oneCommonAncestor) {
            throw new IncorrectConditionException("家族只能存在一个共同祖先");
        }

        // DO -> BO
        PeopleDO ancestorDO = ancestors.get(0);
        MemberTreeNodeBO ancestor = new MemberTreeNodeBO();
        BeanUtils.copyProperties(ancestorDO, ancestor);
        // 获取祖先的伴侣
        ancestor.setMates(this.listWives(ancestor.getId()));
        // 获取祖先的孩子列表
        this.listChildren(ancestor);

        return ancestor;
    }

    @Override
    public Map<String, Object> listGenerationMembers(MemberQuery memberQuery, Long genealogyId) {
        /*
         * 拼接查询条件：姓名 name、性别 gender [0]男 [1]女、生逝状态 [true]生 [false]逝
         * SQL: select * from genealogy_people
         *      where genealogy_id = #{} and
         *      gender = #{} and
         *      name = #{}
         *      and ISNULL(death_date)
         */
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId);
        if (StringUtils.hasLength(memberQuery.getName())) {
            queryWrapper.like("name", memberQuery.getName());
        }
        if (memberQuery.getGender() != null) {
            queryWrapper.eq("gender", memberQuery.getGender());
        }
        if (memberQuery.getAlive() != null) {
            if (memberQuery.getAlive()) {
                // 健在，逝世日期为 null
                queryWrapper.isNull("death_date");
            } else {
                queryWrapper.isNotNull("death_date");
            }
        }

        // 查询出所有符合条件的世代成员
        List<PeopleDO> peopleDOList = this.list(queryWrapper);
        if (peopleDOList == null || peopleDOList.isEmpty()) {
            return null;
        }

        // 将家族成员列表按照世代分组，key = 世代，val = 世代成员列表
        Map<Integer, List<PeopleDO>> generationMemberMap = peopleDOList.stream()
                .collect(Collectors.groupingBy(PeopleDO::getGeneration));
        // 从 Map 中取出所有的键（世代）按升序排列
        List<Integer> generations = new ArrayList<>(generationMemberMap.keySet());
        generations = generations.stream().sorted().collect(Collectors.toList());

        // 将家族世代列表和每个世代下的成员数据封装返回
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("generations", generations);
        resultMap.put("members", generationMemberMap);
        return resultMap;
    }

    @Override
    public List<PeopleCardVO> listMembersByName(String name, Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).like("name", name);
        List<PeopleDO> peopleDOList = this.list(queryWrapper);

        // DO -> VO
        List<PeopleCardVO> resultList = new ArrayList<>();
        for (PeopleDO peopleDO : peopleDOList) {
            PeopleCardVO peopleCardVO = new PeopleCardVO();
            BeanUtils.copyProperties(peopleDO, peopleCardVO);
            resultList.add(peopleCardVO);
        }

        return resultList;
    }

    /**
     * 查询丈夫的妻子列表
     */
    private List<MemberTreeNodeBO> listWives(Long husbandId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("husband_id", husbandId).orderByAsc("seniority");
        List<PeopleDO> wivesDO = this.list(queryWrapper);

        // DO -> VO
        List<MemberTreeNodeBO> resultList = new ArrayList<>();
        for (PeopleDO peopleDO : wivesDO) {
            MemberTreeNodeBO memberTreeNodeBO = new MemberTreeNodeBO();
            BeanUtils.copyProperties(peopleDO, memberTreeNodeBO);
            resultList.add(memberTreeNodeBO);
        }

        return resultList;
    }

    /**
     * 查询父亲的孩子列表
     */
    private void listChildren(MemberTreeNodeBO parent) {
        // 查询父亲下的所有孩子
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("father_id", parent.getId());
        List<PeopleDO> childList = this.list(queryWrapper);
        if (childList == null || childList.isEmpty()) {
            parent.setChildren(null);
            return;
        }

        List<MemberTreeNodeBO> children = new ArrayList<>();
        // 遍历所有孩子，依次查询其妻子和孙子
        childList.forEach(item -> {
            // 创建孩子 BO
            MemberTreeNodeBO child = new MemberTreeNodeBO();
            BeanUtils.copyProperties(item, child);

            // 查询孩子的妻子列表
            child.setMates(this.listWives(child.getId()));

            // 递归查询孩子的孙子列表
            listChildren(child);

            // 将孩子添加到孩子结果列表中
            children.add(child);
        });

        // 将孩子列表设置为父节点的子节点
        parent.setChildren(children);
    }

}
