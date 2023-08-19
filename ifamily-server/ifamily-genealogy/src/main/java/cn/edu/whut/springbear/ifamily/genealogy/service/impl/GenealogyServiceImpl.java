package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.DefaultStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyMemberOverviewDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyMemberVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyMemberOverviewService;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
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
    private SecurityUserService securityUserService;
    @Autowired
    private GenealogyMemberOverviewService genealogyMemberOverviewService;
    @Autowired
    private GenealogyUserService genealogyUserService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean create(GenealogyQuery genealogyQuery) {
        // 用户新增家族，保存家族信息
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        genealogyDO.setId(null);
        UserDO currentUser = securityUserService.getCurrentUser();
        genealogyDO.setCreatorUserId(currentUser.getId());
        Date date = new Date();
        genealogyDO.setCreated(date);
        genealogyDO.setModified(date);
        genealogyDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        this.baseMapper.insert(genealogyDO);

        // 保存默认家族成员概况信息：总人数、男性人数、女性人数、健在人数、已逝人数均为 0
        this.genealogyMemberOverviewService.createDefault(genealogyDO.getId());
        // 修改用户已经关联家族的默认家族状态为否
        this.genealogyUserService.updateAllDefaultGenealogyOfUser(currentUser.getId(), DefaultStatusEnum.NO.getCode());
        // 关联用户当前新建的家族，并将其设为默认家族
        this.genealogyUserService.createUserDefaultGenealogy(currentUser.getId(), genealogyDO.getId());

        return true;
    }

    @Override
    public List<GenealogyMemberVO> listGenealogiesOfUser() {
        UserDO currentUser = this.securityUserService.getCurrentUser();
        // 从家族-用户关联表中查询当前用户关联的所有家族
        List<GenealogyUserDO> genealogyUserDOList = this.genealogyUserService.listGenealogiesOfUser(currentUser.getId());
        if (genealogyUserDOList == null || genealogyUserDOList.isEmpty()) {
            return null;
        }

        List<GenealogyMemberVO> result = new ArrayList<>();
        genealogyUserDOList.forEach(item -> {
            Long gid = item.getGenealogyId();
            GenealogyMemberVO genealogyMemberVO = new GenealogyMemberVO();
            // 查询当前家族的成员概况信息
            GenealogyMemberOverviewDO genealogyMemberOverviewDO = this.genealogyMemberOverviewService.getByGenealogyId(gid);
            BeanUtils.copyProperties(genealogyMemberOverviewDO, genealogyMemberVO);
            // 查询当前家族信息
            GenealogyDO genealogyDO = this.getById(gid);
            BeanUtils.copyProperties(genealogyDO, genealogyMemberVO);
            // 将是否为默认家族信息拷贝到 genealogyMemberVO 中
            genealogyMemberVO.setDefaultGenealogy(item.getDefaultGenealogy());
            result.add(genealogyMemberVO);
        });

        return result;
    }

}
