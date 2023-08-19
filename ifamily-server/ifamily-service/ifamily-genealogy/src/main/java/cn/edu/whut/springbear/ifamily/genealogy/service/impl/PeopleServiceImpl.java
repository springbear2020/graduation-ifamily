package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.constant.GenealogyMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.CRUDEnum;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.GenderEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyProfileService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.RevisionLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:02
 */
@AllArgsConstructor
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, PeopleDO> implements PeopleService {

    private final GenealogyProfileService genealogyProfileService;
    private final RevisionLogService revisionLogService;

    @Override
    public PeopleVO getUserPeople(Long userId, Long genealogyId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("genealogy_id", genealogyId);
        PeopleDO peopleDO = this.getOne(queryWrapper);
        if (peopleDO == null) {
            return null;
        }
        // DO -> VO
        PeopleVO peopleVO = new PeopleVO();
        BeanUtils.copyProperties(peopleDO, peopleVO);
        return peopleVO;
    }

    @Override
    public boolean createUserPeople(PeopleQuery userPeople, Long userId, Long genealogyId) {
        PeopleDO userPeopleDO = new PeopleDO();
        BeanUtils.copyProperties(userPeople, userPeopleDO);
        // 初始化家族人员必要信息
        this.initPeopleDO(userPeopleDO, userPeopleDO.getGeneration(), genealogyId, null, null, null);
        userPeopleDO.setUserId(userId);

        // 保存家族人员信息
        boolean save = this.save(userPeopleDO);
        // 更新家族人员概况信息
        boolean increase = this.genealogyProfileService.peopleIncreaseOne(genealogyId, userPeopleDO.getGender(), userPeopleDO.getDeathDate());
        // 保存家族人员操作日志
        boolean insert = this.revisionLogService.create(CRUDEnum.INSERT.getCode(), userPeople.getName(), userPeopleDO.getId(), genealogyId, userId);
        return save && increase && insert;
    }

    @Override
    public PeopleDetailsBO getPeopleDetails(Long genealogyId, Long peopleId, Long userId) {
        // 查询特定家族中的人员是否存在
        PeopleDO meDO = this.checkGenealogyPeople(genealogyId, peopleId);
        PeopleDetailsBO me = new PeopleDetailsBO();
        // DO -> VO
        PeopleVO meVO = new PeopleVO();
        BeanUtils.copyProperties(meDO, meVO);
        me.setMe(meVO);

        // 存在丈夫时只查询丈夫和孩子信息
        if (meDO.getHusbandId() != null) {
            // 查询丈夫
            PeopleDO husbandDO = this.getById(meDO.getHusbandId());
            if (husbandDO != null) {
                List<PeopleCardVO> husband = new ArrayList<>();
                PeopleCardVO husbandVO = new PeopleCardVO();
                BeanUtils.copyProperties(husbandDO, husbandVO);
                husband.add(husbandVO);
                me.setMates(husband);
            }

            // 查询孩子列表
            List<PeopleCardVO> childrenVO = this.listChildrenOfParent(meDO.getId(), GenderEnum.FEMALE.getCode());
            me.setChildren(childrenVO);
        } else {
            // 查询父亲
            PeopleDO fatherDO = this.getById(meDO.getFatherId());
            if (fatherDO != null) {
                PeopleCardVO fatherVO = new PeopleCardVO();
                BeanUtils.copyProperties(fatherDO, fatherVO);
                me.setFather(fatherVO);
            }
            // 查询母亲
            PeopleDO motherDO = this.getById(meDO.getMotherId());
            if (motherDO != null) {
                PeopleCardVO motherVO = new PeopleCardVO();
                BeanUtils.copyProperties(motherDO, motherVO);
                me.setMother(motherVO);
            }

            // 查询妻子列表
            List<PeopleCardVO> wivesVO = this.listWivesOfHusband(meDO.getId());
            me.setMates(wivesVO);

            // 查询孩子列表
            List<PeopleCardVO> children = this.listChildrenOfParent(meDO.getId(), GenderEnum.MALE.getCode());
            me.setChildren(children);

            // 查询同胞列表，即查询我父亲的孩子，但排除我
            List<PeopleCardVO> compatriots = this.listChildrenOfParent(meDO.getFatherId(), GenderEnum.MALE.getCode());
            compatriots = compatriots.stream().filter(item -> !item.getId().equals(meDO.getId())).collect(Collectors.toList());
            me.setCompatriots(compatriots);
        }

        // 保存家族人员查看日志
        this.revisionLogService.create(CRUDEnum.SELECT.getCode(), meDO.getName(), meDO.getId(), meDO.getGenealogyId(), userId);

        return me;
    }

    @Override
    public PeopleVO getGenealogyPeople(Long genealogyId, Long peopleId) {
        PeopleDO peopleDO = this.checkGenealogyPeople(genealogyId, peopleId);
        PeopleVO peopleVO = new PeopleVO();
        BeanUtils.copyProperties(peopleDO, peopleVO);
        return peopleVO;
    }

    @Override
    public boolean editGenealogyPeople(Long genealogyId, PeopleQuery peopleQuery, Long userId) {
        PeopleVO peopleVO = this.getGenealogyPeople(genealogyId, peopleQuery.getId());
        if (peopleVO == null) {
            throw new IncorrectConditionException(GenealogyMessageConstants.PEOPLE_NOT_EXISTS);
        }

        // 检查人员性别是否发生改变，若发生改变则更新家族概况中的男、女人数
        this.genealogyProfileService.changeByGender(peopleQuery.getGender(), peopleVO.getGender(), genealogyId);
        // 检查人员逝世日期是否发生改变，若发生改变则更新家族概况中的生、逝人数
        this.genealogyProfileService.changeByDeath(peopleQuery.getDeathDate(), peopleVO.getDeathDate(), genealogyId);
        // 保存家族人员编辑日志
        boolean create = this.revisionLogService.create(CRUDEnum.UPDATE.getCode(), peopleVO.getName(), peopleVO.getId(), genealogyId, userId);
        // 更新家族人员信息
        PeopleDO peopleUpdateDO = new PeopleDO();
        BeanUtils.copyProperties(peopleQuery, peopleUpdateDO);
        boolean update = this.updateById(peopleUpdateDO);

        return create && update;
    }

    @Override
    public boolean removeGenealogyPeople(Long genealogyId, Long peopleId, Long userId) {
        PeopleVO peopleVO = this.getGenealogyPeople(genealogyId, peopleId);
        if (peopleVO == null) {
            throw new IncorrectConditionException(GenealogyMessageConstants.PEOPLE_NOT_EXISTS);
        }

        // 保存家族人员删除日志
        boolean create = this.revisionLogService.create(CRUDEnum.DELETE.getCode(), peopleVO.getName(), peopleId, genealogyId, userId);
        // 更新家族成员概况信息，总人数 -1，对应男或女人数 -1，对应生或逝人数 -1
        boolean decreaseOne = this.genealogyProfileService.peopleDecreaseOne(genealogyId, peopleVO.getGender(), peopleVO.getDeathDate());
        // 删除家族人员
        boolean remove = this.removeById(peopleId);

        return create && decreaseOne && remove;
    }

    @Override
    public boolean saveGenealogyPeopleFather(Long genealogyId, Long mePeopleId, PeopleQuery fatherInfo, Long userId) {
        PeopleDO meDO = this.checkGenealogyPeople(genealogyId, mePeopleId);
        // 不存在生父、不存在丈夫：允许添加生父
        if (meDO.getFatherId() != null) {
            throw new IncorrectConditionException("生父信息已存在，不可重复添加");
        }
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IncorrectConditionException("不存在丈夫时方可添加生父信息");
        }

        PeopleDO fatherDO = new PeopleDO();
        BeanUtils.copyProperties(fatherInfo, fatherDO);
        // 我的生父世代比 “我” 小 1，需注意添加生父后其为家族根祖先时应将所有家族人员的世代增 1，而生父作为祖先，世代为 1
        final int generationLimit = 0;
        int fatherGeneration = meDO.getGeneration() - 1;
        if (fatherGeneration <= generationLimit) {
            this.baseMapper.generationIncreaseOne(meDO.getGenealogyId());
            fatherGeneration = 1;
        }
        // 设置生父必要信息：性别男、世代比我小 1
        fatherDO.setGender(GenderEnum.MALE.getCode());
        this.initPeopleDO(fatherDO, fatherGeneration, genealogyId, null, null, null);

        // 保存生父信息
        boolean save = this.save(fatherDO);
        // 家族总人数和男性人数 +1，根据逝世日期是否为空相应人数 +1
        boolean increaseOne = this.genealogyProfileService.peopleIncreaseOne(meDO.getGenealogyId(), GenderEnum.MALE.getCode(), fatherDO.getDeathDate());
        // 保存家族人员新增日志
        boolean create = this.revisionLogService.create(CRUDEnum.INSERT.getCode(), fatherDO.getName(), fatherDO.getId(), meDO.getGenealogyId(), userId);
        // 更新 “我” 的 father_id
        PeopleDO meUpdate = new PeopleDO();
        meUpdate.setId(meDO.getId());
        meUpdate.setFatherId(fatherDO.getId());
        boolean update = this.updateById(meUpdate);

        return save && update && increaseOne && create;
    }

    @Override
    public boolean saveGenealogyPeopleMother(Long genealogyId, Long mePeopleId, PeopleQuery motherInfo, Long userId) {
        PeopleDO meDO = this.checkGenealogyPeople(genealogyId, mePeopleId);
        // 存在生父、不存在生母、不存在丈夫：允许添加生母
        if (meDO.getFatherId() == null) {
            throw new IncorrectConditionException("请先添加生父后再添加生母信息");
        }
        if (meDO.getMotherId() != null) {
            throw new IncorrectConditionException("生母信息已存在，不可重复添加");
        }
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IncorrectConditionException("不存在丈夫时方可添加生母信息");
        }

        // 设置生母的必要信息：性别女、世代比我小 1、丈夫 ID 为我生父的 ID
        PeopleDO motherDO = new PeopleDO();
        BeanUtils.copyProperties(motherInfo, motherDO);
        motherDO.setGender(GenderEnum.FEMALE.getCode());
        this.initPeopleDO(motherDO, meDO.getGeneration() - 1, meDO.getGenealogyId(), meDO.getFatherId(), null, null);

        // 保存生母信息
        boolean save = this.save(motherDO);
        // 家族总人数和女性人数 +1，根据逝世日期是否为空相应人数 +1
        boolean increaseOne = this.genealogyProfileService.peopleIncreaseOne(meDO.getGenealogyId(), GenderEnum.FEMALE.getCode(), motherDO.getDeathDate());
        // 保存家族人员新增日志
        boolean create = this.revisionLogService.create(CRUDEnum.INSERT.getCode(), motherDO.getName(), motherDO.getId(), meDO.getGenealogyId(), userId);
        // 更新 “我” 的 mother_id
        PeopleDO meUpdate = new PeopleDO();
        meUpdate.setId(meDO.getId());
        meUpdate.setMotherId(motherDO.getId());
        boolean update = this.updateById(meUpdate);

        return save && update && increaseOne && create;
    }

    @Override
    public boolean saveGenealogyPeopleWife(Long genealogyId, Long mePeopleId, PeopleQuery wifeInfo, Long userId) {
        PeopleDO meDO = this.checkGenealogyPeople(genealogyId, mePeopleId);
        // 不存在丈夫：允许添加配偶
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IncorrectConditionException("不存在丈夫时方可添加配偶信息");
        }

        // 设置妻子的必要信息：性别为女、世代和字辈与我一致，丈夫 ID 为我的 ID
        PeopleDO wifeDO = new PeopleDO();
        BeanUtils.copyProperties(wifeInfo, wifeDO);
        wifeDO.setGender(GenderEnum.FEMALE.getCode());
        wifeDO.setGenerationName(wifeDO.getGenerationName());
        this.initPeopleDO(wifeDO, meDO.getGeneration(), meDO.getGenealogyId(), mePeopleId, null, null);

        // 保存妻子信息
        boolean save = this.save(wifeDO);
        // 家族总人数和女性人数 +1，根据逝世日期是否为空相应人数 +1
        boolean increaseOne = this.genealogyProfileService.peopleIncreaseOne(meDO.getGenealogyId(), GenderEnum.FEMALE.getCode(), wifeDO.getDeathDate());
        // 保存家族人员新增日志
        boolean create = this.revisionLogService.create(CRUDEnum.INSERT.getCode(), wifeDO.getName(), wifeDO.getId(), meDO.getGenealogyId(), userId);

        return save && increaseOne && create;
    }

    @Override
    public boolean saveGenealogyPeopleChild(Long genealogyId, Long mePeopleId, PeopleQuery childInfo, Long userId) {
        PeopleDO meDO = this.checkGenealogyPeople(genealogyId, mePeopleId);
        PeopleDO childDO = new PeopleDO();
        BeanUtils.copyProperties(childInfo, childDO);

        /*
         * 1. 妻子存在丈夫：允许添加子女
         * 2. 丈夫有且仅有一位妻子：允许添加子女
         */
        if (meDO.getHusbandId() != null) {
            // 设置孩子的生父、生母 ID
            childDO.setFatherId(meDO.getHusbandId());
            childDO.setMotherId(meDO.getId());
        } else {
            // 查询 “我” 的妻子列表
            List<PeopleCardVO> wives = this.listWivesOfHusband(mePeopleId);
            if (wives.isEmpty()) {
                throw new IncorrectConditionException("请先添加配偶后再添加子女信息");
            }
            // 若 “我” 的妻子列表人数大于 1，提示通过配偶添加子女从而明确子女的生母关系
            final int oneWifeLimit = 1;
            if (wives.size() > oneWifeLimit) {
                throw new IncorrectConditionException("检测到您存在多个配偶，请通过配偶添加子女，从而明确子女的生母关系");
            }

            childDO.setFatherId(meDO.getId());
            childDO.setMotherId(wives.get(0).getId());
        }

        // 设置孩子的必要信息：世代比我大 1
        this.initPeopleDO(childDO, meDO.getGeneration() + 1, meDO.getGenealogyId(), null, childDO.getFatherId(), childDO.getMotherId());

        // 保存孩子信息
        boolean save = this.save(childDO);
        // 家族总人数 +1，根据孩子性别将男性或女性人数 +1，根据孩子逝世日期是否为空将健在或已逝人数 +1
        boolean increaseOne = this.genealogyProfileService.peopleIncreaseOne(meDO.getGenealogyId(), childDO.getGender(), childDO.getDeathDate());
        // 保存家族人员新增日志
        boolean create = this.revisionLogService.create(CRUDEnum.INSERT.getCode(), childDO.getName(), childDO.getId(), meDO.getGenealogyId(), userId);

        return save && increaseOne && create;
    }

    @Override
    public boolean saveGenealogyPeopleCompatriot(Long genealogyId, Long mePeopleId, PeopleQuery compatriotInfo, Long userId) {
        PeopleDO meDO = this.checkGenealogyPeople(genealogyId, mePeopleId);
        // 不存在丈夫、存在生父、存在生母：允许添加同胞
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IncorrectConditionException("不存在丈夫时方可添加同胞信息");
        }
        if (meDO.getFatherId() == null) {
            throw new IncorrectConditionException("请先添加生父后再添加同胞信息");
        }
        if (meDO.getMotherId() == null) {
            throw new IncorrectConditionException("请先添加生母后再添加同胞信息");
        }

        // 设置同胞的必要信息：世代、字辈、生父、生母与我一致
        PeopleDO compatriotDO = new PeopleDO();
        BeanUtils.copyProperties(compatriotInfo, compatriotDO);
        compatriotDO.setGenerationName(meDO.getGenerationName());
        this.initPeopleDO(compatriotDO, meDO.getGeneration(), meDO.getGenealogyId(), null, meDO.getFatherId(), meDO.getMotherId());

        // 保存同胞信息
        boolean save = this.save(compatriotDO);
        // 家族总人数 +1，根据同胞性别将男性或女性人数 +1，根据同胞逝世日期是否为空将健在或已逝人数 +1
        boolean increaseOne = this.genealogyProfileService.peopleIncreaseOne(meDO.getGenealogyId(), compatriotDO.getGender(), compatriotDO.getDeathDate());
        // 保存家族人员新增日志
        boolean create = this.revisionLogService.create(CRUDEnum.INSERT.getCode(), compatriotDO.getName(), compatriotDO.getId(), meDO.getGenealogyId(), userId);

        return save && increaseOne && create;
    }

    /**
     * 查询父亲或母亲下的孩子列表
     *
     * @param parentId   父亲或母亲 ID
     * @param parentType 父母类型：[0]父亲 [1]母亲
     */
    private List<PeopleCardVO> listChildrenOfParent(Long parentId, int parentType) {
        String column = parentType == 0 ? "father_id" : "mother_id";
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, parentId).orderByAsc("seniority");
        List<PeopleDO> childrenDOList = this.list(queryWrapper);
        return this.convertPeopleDOList(childrenDOList);
    }

    /**
     * 查询丈夫的所有妻子
     */
    private List<PeopleCardVO> listWivesOfHusband(Long husbandId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("husband_id", husbandId).orderByAsc("seniority");
        List<PeopleDO> wivesDO = this.list(queryWrapper);
        return this.convertPeopleDOList(wivesDO);
    }

    /**
     * 检查家族人员信息是否存在
     *
     * @param genealogyId 家族 ID
     * @param peopleId    人员 ID
     * @return 家族人员信息
     */
    private PeopleDO checkGenealogyPeople(Long genealogyId, Long peopleId) {
        // 验证家族中的人员信息是否存在
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("genealogy_id", genealogyId).eq("id", peopleId);
        PeopleDO peopleDO = this.getOne(queryWrapper);
        if (peopleDO == null) {
            throw new IncorrectConditionException(GenealogyMessageConstants.PEOPLE_NOT_EXISTS);
        }
        return peopleDO;
    }

    /**
     * 将 PeopleDO 列表转换为 PeopleCardVO 列表
     */
    private List<PeopleCardVO> convertPeopleDOList(List<PeopleDO> peopleDOList) {
        List<PeopleCardVO> peopleVOList = new ArrayList<>();
        for (PeopleDO peopleDO : peopleDOList) {
            PeopleCardVO peopleCardVO = new PeopleCardVO();
            BeanUtils.copyProperties(peopleDO, peopleCardVO);
            peopleVOList.add(peopleCardVO);
        }
        return peopleVOList;
    }

    /**
     * 初始化家族人员的必要信息
     */
    private void initPeopleDO(PeopleDO peopleDO, Integer generation, Long genealogyId, Long husbandId, Long fatherId, Long motherId) {
        peopleDO.setGeneration(generation);
        peopleDO.setGenealogyId(genealogyId);
        peopleDO.setHusbandId(husbandId);
        peopleDO.setFatherId(fatherId);
        peopleDO.setMotherId(motherId);
        peopleDO.setId(null);
        peopleDO.setUserId(null);
        Date date = new Date();
        peopleDO.setCreated(date);
        peopleDO.setModified(date);
        peopleDO.setDeleted(AssertEnum.NO.getCode());
    }

}
