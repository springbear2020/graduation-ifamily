package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IllegalConditionException;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.GenderEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyProfileService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:02
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, PeopleDO> implements PeopleService {

    @Autowired
    private GenealogyProfileService genealogyProfileService;

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

    @Override
    public PeopleDetailsBO getGenealogyPeopleProfileDetails(Long peopleId) {
        PeopleDO meDO = this.baseMapper.selectById(peopleId);
        if (meDO == null) {
            return null;
        }

        PeopleDetailsBO me = new PeopleDetailsBO();
        // 个人信息 DO 转 VO
        PeopleVO meVO = new PeopleVO();
        BeanUtils.copyProperties(meDO, meVO);
        me.setMe(meVO);

        // 存在丈夫时只查询丈夫和孩子信息
        if (meDO.getHusbandId() != null) {
            // 查询丈夫
            PeopleDO husbandDO = this.baseMapper.selectById(meDO.getHusbandId());
            if (husbandDO != null) {
                List<PeopleCardVO> husband = new ArrayList<>();
                PeopleCardVO husbandVO = new PeopleCardVO();
                BeanUtils.copyProperties(husbandDO, husbandVO);
                husband.add(husbandVO);
                me.setMates(husband);
            }

            // 查询孩子列表
            final int motherType = 1;
            List<PeopleCardVO> childrenVO = this.listChildrenOfParent(meDO.getId(), motherType);
            me.setChildren(childrenVO);
        } else {
            // 查询父亲
            PeopleDO fatherDO = this.baseMapper.selectById(meDO.getFatherId());
            if (fatherDO != null) {
                PeopleCardVO fatherVO = new PeopleCardVO();
                BeanUtils.copyProperties(fatherDO, fatherVO);
                me.setFather(fatherVO);
            }
            // 查询母亲
            PeopleDO motherDO = this.baseMapper.selectById(meDO.getMotherId());
            if (motherDO != null) {
                PeopleCardVO motherVO = new PeopleCardVO();
                BeanUtils.copyProperties(motherDO, motherVO);
                me.setMother(motherVO);
            }

            // 查询妻子列表
            List<PeopleCardVO> wivesVO = this.listWivesOfPeople(meDO.getId());
            me.setMates(wivesVO);

            // 查询孩子列表
            final int fatherType = 0;
            List<PeopleCardVO> children = this.listChildrenOfParent(meDO.getId(), fatherType);
            me.setChildren(children);

            // 查询同胞列表，即查询我父亲的孩子，但排除我
            List<PeopleCardVO> compatriots = this.listChildrenOfParent(meDO.getFatherId(), fatherType);
            compatriots = compatriots.stream().filter(item -> !item.getId().equals(meDO.getId())).collect(Collectors.toList());
            me.setCompatriots(compatriots);
        }

        return me;
    }

    @Override
    public PeopleVO getPeopleById(Long peopleId) {
        PeopleDO peopleDO = this.baseMapper.selectById(peopleId);
        if (peopleDO == null) {
            return null;
        }
        PeopleVO peopleVO = new PeopleVO();
        BeanUtils.copyProperties(peopleDO, peopleVO);
        return peopleVO;
    }

    @Override
    public boolean updatePeopleById(PeopleQuery peopleQuery) {
        PeopleDO peopleDO = new PeopleDO();
        BeanUtils.copyProperties(peopleQuery, peopleDO);
        return this.baseMapper.updateById(peopleDO) == 1;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean savePeopleFather(Long mePeopleId, PeopleQuery fatherInfo) {
        // 不存在生父、不存在丈夫：允许添加生父
        PeopleDO meDO = this.getById(mePeopleId);
        if (meDO.getFatherId() != null) {
            throw new IllegalConditionException("生父信息已存在，不可重复添加");
        }
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IllegalConditionException("不存在丈夫信息时方可添加生父信息");
        }

        PeopleDO fatherDO = new PeopleDO();
        BeanUtils.copyProperties(fatherInfo, fatherDO);
        // 我的生父世代比 “我” 小 1，需注意添加生父后其为家族祖先时应将所有家族人员的世代增 1。生父作为祖先，世代为 1
        final int generationLimit = 0;
        int fatherGeneration = meDO.getGeneration() - 1;
        if (fatherGeneration == generationLimit) {
            this.baseMapper.membersGenerationIncreaseOne(meDO.getGenealogyId());
            fatherGeneration = 1;
        }
        fatherDO.setGender(GenderEnum.MALE.getCode());
        // 设置生父必要信息
        this.initRelativeDO(fatherDO, fatherGeneration, meDO.getGenealogyId(), null, null, null);

        // 保存生父信息
        int affectedRows = this.baseMapper.insert(fatherDO);
        if (affectedRows != 1) {
            return false;
        }

        // 更新 “我” 的 father_id
        PeopleDO meUpdate = new PeopleDO();
        meUpdate.setId(meDO.getId());
        meUpdate.setFatherId(fatherDO.getId());
        affectedRows += this.baseMapper.updateById(meUpdate);

        // 家族总人数和男性人数 +1，根据逝世日期是否为空相应人数 +1
        affectedRows += this.genealogyProfileService.genealogyPeopleProfileIncreaseOne(meDO.getGenealogyId(), GenderEnum.MALE.getCode(), fatherDO.getDeathDate() != null);

        return affectedRows == 3;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean savePeopleMother(Long mePeopleId, PeopleQuery motherInfo) {
        // 存在生父、不存在生母、不存在丈夫：允许添加生母
        PeopleDO meDO = this.getById(mePeopleId);
        if (meDO.getFatherId() == null) {
            throw new IllegalConditionException("请先添加生父信息后再添加生母信息");
        }
        if (meDO.getMotherId() != null) {
            throw new IllegalConditionException("生母信息已存在，不可重复添加");
        }
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IllegalConditionException("不存在丈夫信息时方可添加生母信息");
        }

        PeopleDO motherDO = new PeopleDO();
        BeanUtils.copyProperties(motherInfo, motherDO);
        // 设置生母的必要信息：世代比我小 1，丈夫 ID 为我生父 ID
        motherDO.setGender(GenderEnum.FEMALE.getCode());
        this.initRelativeDO(motherDO, meDO.getGeneration() - 1, meDO.getGenealogyId(), meDO.getFatherId(), null, null);

        // 保存生母信息
        int affectedRows = this.baseMapper.insert(motherDO);
        if (affectedRows != 1) {
            return false;
        }

        // 更新 “我” 的 mother_id
        PeopleDO meUpdate = new PeopleDO();
        meUpdate.setId(meDO.getId());
        meUpdate.setMotherId(motherDO.getId());
        affectedRows += this.baseMapper.updateById(meUpdate);

        // 家族总人数和女性人数 +1，根据逝世日期是否为空相应人数 +1
        affectedRows += this.genealogyProfileService.genealogyPeopleProfileIncreaseOne(meDO.getGenealogyId(), GenderEnum.FEMALE.getCode(), motherDO.getDeathDate() != null);

        return affectedRows == 3;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean savePeopleWife(Long mePeopleId, PeopleQuery wifeInfo) {
        // 不存在丈夫：允许添加配偶
        PeopleDO meDO = this.getById(mePeopleId);
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IllegalConditionException("不存在丈夫信息时方可添加配偶信息");
        }

        PeopleDO wifeDO = new PeopleDO();
        BeanUtils.copyProperties(wifeInfo, wifeDO);
        wifeDO.setGender(GenderEnum.FEMALE.getCode());
        wifeDO.setGenerationName(wifeDO.getGenerationName());
        // 设置我妻子的必要信息：世代、字辈与我一致，丈夫 ID 为我的 ID
        this.initRelativeDO(wifeDO, meDO.getGeneration(), meDO.getGenealogyId(), mePeopleId, null, null);

        // 保存妻子信息
        int affectedRows = this.baseMapper.insert(wifeDO);
        if (affectedRows != 1) {
            return false;
        }

        // 家族总人数和女性人数 +1，根据逝世日期是否为空相应人数 +1
        affectedRows += this.genealogyProfileService.genealogyPeopleProfileIncreaseOne(meDO.getGenealogyId(), GenderEnum.FEMALE.getCode(), wifeDO.getDeathDate() != null);

        return affectedRows == 2;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean savePeopleChild(Long mePeopleId, PeopleQuery childInfo) {
        PeopleDO meDO = this.getById(mePeopleId);
        PeopleDO childDO = new PeopleDO();
        BeanUtils.copyProperties(childInfo, childDO);

        /*
         * 1. 存在丈夫：允许添加子女
         * 2. 不存在丈夫、有且仅有一个妻子：允许添加子女
         */
        if (meDO.getHusbandId() != null) {
            // 设置孩子的生父、生母 ID
            childDO.setFatherId(meDO.getHusbandId());
            childDO.setMotherId(meDO.getId());
        } else {
            // 查询 “我” 的妻子列表，若人数大于 1 时提示通过我的妻子添加子女以明确生母与孩子间的关系
            List<PeopleCardVO> wives = this.listWivesOfPeople(mePeopleId);
            if (wives.isEmpty()) {
                throw new IllegalConditionException("请先添加配偶信息后再添加子女信息");
            }
            final int oneWifeLimit = 1;
            if (wives.size() > oneWifeLimit) {
                throw new IllegalConditionException("检测到您存在多个配偶，请通过配偶添加子女，从而明确子女的生母关系");
            }

            childDO.setFatherId(meDO.getId());
            childDO.setMotherId(wives.get(0).getId());
        }
        // 我的孩子世代比我大 1
        this.initRelativeDO(childDO, meDO.getGeneration() + 1, meDO.getGenealogyId(), null, childDO.getFatherId(), childDO.getMotherId());

        // 保存孩子信息
        int affectedRows = this.baseMapper.insert(childDO);
        if (affectedRows != 1) {
            return false;
        }

        // 家族总人数 +1，根据孩子性别将男性或女性人数 +1，根据孩子逝世日期是否为空将健在或已逝人数 +1
        affectedRows += this.genealogyProfileService.genealogyPeopleProfileIncreaseOne(meDO.getGenealogyId(), childDO.getGender(), childDO.getDeathDate() != null);

        return affectedRows == 2;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean savePeopleCompatriot(Long mePeopleId, PeopleQuery compatriotInfo) {
        // 不存在丈夫、存在生父、存在生母：允许添加同胞
        PeopleDO meDO = this.getById(mePeopleId);
        if (GenderEnum.FEMALE.getCode().equals(meDO.getGender())) {
            throw new IllegalConditionException("不存在丈夫信息时方可添加同胞信息");
        }
        if (meDO.getFatherId() == null) {
            throw new IllegalConditionException("请先添加生父信息后再添加同胞信息");
        }
        if (meDO.getMotherId() == null) {
            throw new IllegalConditionException("请先添加生母信息后再添加同胞信息");
        }

        PeopleDO compatriotDO = new PeopleDO();
        BeanUtils.copyProperties(compatriotInfo, compatriotDO);
        // 我的同胞世代、字辈、生父、生母与我一致
        compatriotDO.setGenerationName(meDO.getGenerationName());
        this.initRelativeDO(compatriotDO, meDO.getGeneration(), meDO.getGenealogyId(), null, meDO.getFatherId(), meDO.getMotherId());

        // 保存同胞信息
        int affectedRows = this.baseMapper.insert(compatriotDO);
        if (affectedRows != 1) {
            return false;
        }

        // 家族总人数 +1，根据同胞性别将男性或女性人数 +1，根据同胞逝世日期是否为空将健在或已逝人数 +1
        affectedRows += this.genealogyProfileService.genealogyPeopleProfileIncreaseOne(meDO.getGenealogyId(), compatriotDO.getGender(), compatriotDO.getDeathDate() != null);

        return affectedRows == 2;
    }

    /**
     * 初始化亲人的必要信息
     */
    private void initRelativeDO(PeopleDO relativeDO, Integer generation, Long genealogyId, Long husbandId, Long fatherId, Long motherId) {
        relativeDO.setGeneration(generation);
        relativeDO.setGenealogyId(genealogyId);
        relativeDO.setHusbandId(husbandId);
        relativeDO.setFatherId(fatherId);
        relativeDO.setMotherId(motherId);
        relativeDO.setId(null);
        relativeDO.setUserId(null);
        Date date = new Date();
        relativeDO.setCreated(date);
        relativeDO.setModified(date);
        relativeDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
    }

    /**
     * 查询父亲或母亲下的孩子列表
     *
     * @param parentId 父亲或母亲 ID
     * @param type     [0]父亲 ID [1]母亲 ID
     */
    private List<PeopleCardVO> listChildrenOfParent(Long parentId, int type) {
        String column = type == 0 ? "father_id" : "mother_id";
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, parentId).orderByAsc("seniority");
        List<PeopleDO> childrenDOList = this.baseMapper.selectList(queryWrapper);
        return this.convertPeopleDO(childrenDOList);
    }

    /**
     * 查询丈夫的所有妻子
     */
    private List<PeopleCardVO> listWivesOfPeople(Long husbandId) {
        QueryWrapper<PeopleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("husband_id", husbandId).orderByAsc("seniority");
        List<PeopleDO> wivesDO = this.baseMapper.selectList(queryWrapper);
        return this.convertPeopleDO(wivesDO);
    }

    /**
     * 将 PeopleDO 列表转换为 PeopleCardVO 列表
     */
    private List<PeopleCardVO> convertPeopleDO(List<PeopleDO> peopleDOList) {
        List<PeopleCardVO> peopleVOList = new ArrayList<>();
        peopleDOList.forEach(item -> {
            PeopleCardVO peopleCardVO = new PeopleCardVO();
            BeanUtils.copyProperties(item, peopleCardVO);
            peopleVOList.add(peopleCardVO);
        });
        return peopleVOList;
    }

}
