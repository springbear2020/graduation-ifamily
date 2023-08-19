package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyProfileDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:57
 */
@Service
public class GenealogyServiceImpl extends ServiceImpl<GenealogyMapper, GenealogyDO> implements GenealogyService {

    @Autowired
    private GenealogyProfileService genealogyProfileService;
    @Autowired
    private UserGenealogyService userGenealogyService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private VisitorLogService visitorLogService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean create(GenealogyQuery genealogyQuery, Long userId) {
        // 用户新增家族，保存家族信息
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        genealogyDO.setId(null);
        genealogyDO.setCreatorUserId(userId);
        Date date = new Date();
        genealogyDO.setCreated(date);
        genealogyDO.setModified(date);
        genealogyDO.setDeleted(AssertEnum.NO.getCode());

        // 保存家族
        int affectedRows = 0;
        affectedRows += this.baseMapper.insert(genealogyDO);
        // 保存家族成员概况默认信息
        this.genealogyProfileService.create(genealogyDO.getId());
        // 修改用户已经关联家族的默认家族状态为否
        this.userGenealogyService.updateDefault(userId, AssertEnum.NO.getCode());
        // 关联用户当前新建的家族，并将其设为默认家族
        this.userGenealogyService.createDefault(userId, genealogyDO.getId());

        return affectedRows == 1;
    }

    @Override
    public List<GenealogyDetailsBO> listWithDetailsOfUser(Long userId) {
        // 从家族-用户关联表中查询当前用户关联的所有家族
        List<UserGenealogyDO> userGenealogyDOList = this.userGenealogyService.listGenealogiesOfUser(userId);
        if (userGenealogyDOList == null || userGenealogyDOList.isEmpty()) {
            return null;
        }

        List<GenealogyDetailsBO> result = new ArrayList<>();
        // 遍历用户关联的所有家族，依次查询每个家族的成员概况概况信息、家族信息、家族管理员信息
        userGenealogyDOList.forEach(item -> {
            Long gid = item.getGenealogyId();
            GenealogyDetailsBO genealogyDetailsBO = new GenealogyDetailsBO();
            // 查询当前家族的成员概况信息
            GenealogyProfileDO overviewDO = this.genealogyProfileService.getByGenealogyId(gid);
            BeanUtils.copyProperties(overviewDO, genealogyDetailsBO);
            // 查询当前家族信息
            GenealogyDO genealogyDO = this.getById(gid);
            BeanUtils.copyProperties(genealogyDO, genealogyDetailsBO);
            // 查询家族创建者信息
            PeopleVO genealogyCreator = this.peopleService.getByUserGenealogyId(genealogyDO.getCreatorUserId(), item.getGenealogyId());
            PeopleCardVO genealogyCreatorVO = new PeopleCardVO();
            BeanUtils.copyProperties(genealogyCreator, genealogyCreatorVO);
            genealogyDetailsBO.setCreator(genealogyCreatorVO);
            // 查询家族管理员列表信息
            List<Long> genealogyAdminIds = this.userGenealogyService.listAdminIdsOfGenealogy(item.getGenealogyId());
            List<PeopleCardVO> genealogyAdmins = this.memberService.listMemberByBatchUserIds(genealogyAdminIds, item.getGenealogyId());
            genealogyDetailsBO.setAdmins(genealogyAdmins);
            // 设置当前用户家族的默认状态
            genealogyDetailsBO.setDefaultGenealogy(item.getDefaultGenealogy());
            result.add(genealogyDetailsBO);

            // 保存用户默认家族的访问记录
            if (AssertEnum.YES.getCode().equals(item.getDefaultGenealogy())) {
                visitorLogService.create(userId, item.getGenealogyId());
            }
        });

        return result;
    }

    @Override
    public boolean edit(GenealogyQuery genealogyQuery) {
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        return this.baseMapper.updateById(genealogyDO) == 1;
    }

}
