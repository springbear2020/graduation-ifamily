package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyProfileDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.*;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:57
 */
@AllArgsConstructor
@Service
public class GenealogyServiceImpl extends ServiceImpl<GenealogyMapper, GenealogyDO> implements GenealogyService {

    private final UserGenealogyService userGenealogyService;
    private final GenealogyProfileService genealogyProfileService;
    private final PeopleService peopleService;
    private final MemberService memberService;
    private final VisitorLogService visitorLogService;

    @Override
    public boolean create(GenealogyQuery genealogyQuery, Long creatorUserId) {
        // 设置用户家族必要信息
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        genealogyDO.setId(null);
        genealogyDO.setCreatorUserId(creatorUserId);
        Date date = new Date();
        genealogyDO.setCreated(date);
        genealogyDO.setModified(date);
        genealogyDO.setDeleted(AssertEnum.NO.getCode());

        // 保存家族
        boolean save = this.save(genealogyDO);
        // 将新增的家族与用户关联，并设置为用户的默认家族，且将用户设置为家族管理员
        boolean createAndAssociate = this.userGenealogyService.createAndAssociate(creatorUserId, genealogyDO.getId());
        // 保存家族成员概况默认信息：所有人数均为 0
        boolean createDefault = this.genealogyProfileService.createDefault(genealogyDO.getId());

        return save && createAndAssociate && createDefault;
    }

    @Override
    public List<GenealogyDetailsBO> listWithDetails(Long userId) {
        // 从家族-用户关联表中查询当前用户关联的所有家族
        List<UserGenealogyDO> userGenealogyDOList = this.userGenealogyService.listGenealogiesOfUser(userId);
        if (userGenealogyDOList == null || userGenealogyDOList.isEmpty()) {
            return null;
        }

        List<GenealogyDetailsBO> resultList = new ArrayList<>();
        // 遍历用户的所有家族，依次查询每个家族的成员概况信息、家族信息、创建者信息和家族管理员信息
        for (UserGenealogyDO item : userGenealogyDOList) {
            GenealogyDetailsBO genealogyDetailsBO = new GenealogyDetailsBO();
            final Long genealogyId = item.getGenealogyId();

            // 查询当前家族的成员概况信息
            GenealogyProfileDO genealogyProfileDO = this.genealogyProfileService.getByGenealogyId(genealogyId);
            BeanUtils.copyProperties(genealogyProfileDO, genealogyDetailsBO);
            // 查询当前家族信息
            GenealogyDO genealogyDO = this.getById(genealogyId);
            BeanUtils.copyProperties(genealogyDO, genealogyDetailsBO);
            // 设置当前用户家族的默认家族状态
            genealogyDetailsBO.setDefaultGenealogy(item.getDefaultGenealogy());

            // 查询家族创建者信息
            PeopleVO creatorVO = this.peopleService.getUserPeople(genealogyDO.getCreatorUserId(), genealogyId);
            if (creatorVO != null) {
                PeopleCardVO creatorCardVO = new PeopleCardVO();
                BeanUtils.copyProperties(creatorVO, creatorCardVO);
                genealogyDetailsBO.setCreator(creatorCardVO);
            }
            // 查询家族管理员列表信息
            List<Long> genealogyAdminIds = this.userGenealogyService.listAdminIdsOfGenealogy(genealogyId);
            List<PeopleDO> genealogyAdminDOList = this.memberService.listMemberInBatchUserIds(genealogyAdminIds, genealogyId);
            if (CollUtil.isNotEmpty(genealogyAdminDOList)) {
                List<PeopleCardVO> genealogyAdmins = new ArrayList<>();
                // DO -> VO
                for (PeopleDO adminDO : genealogyAdminDOList) {
                    PeopleCardVO adminVO = new PeopleCardVO();
                    BeanUtils.copyProperties(adminDO, adminVO);
                    genealogyAdmins.add(adminVO);
                }
                genealogyDetailsBO.setAdmins(genealogyAdmins);
            }

            resultList.add(genealogyDetailsBO);

            // 保存用户默认家族的访问记录
            if (AssertEnum.YES.getCode().equals(item.getDefaultGenealogy())) {
                if (!this.visitorLogService.userVisitedGenealogyToday(userId, genealogyId)) {
                    this.visitorLogService.create(userId, genealogyId);
                }
            }
        }

        return resultList;
    }

    @Override
    public boolean edit(GenealogyQuery genealogyQuery, Long genealogyId) {
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        genealogyDO.setId(genealogyId);
        return this.updateById(genealogyDO);
    }

}
