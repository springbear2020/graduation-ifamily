package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.enumerate.DefaultStatusEnum;
import cn.edu.whut.springbear.ifamily.genealogy.mapper.GenealogyMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyProfileDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyVO;
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
    private GenealogyUserService genealogyUserService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private GenealogyAdminService genealogyAdminService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean create(GenealogyQuery genealogyQuery, Long userId) {
        // 用户新增家族，保存家族信息
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        // 家族 ID 置空，由服务器生成
        genealogyDO.setId(null);
        // 创建者用户 ID
        genealogyDO.setCreatorUserId(userId);
        Date date = new Date();
        genealogyDO.setCreated(date);
        genealogyDO.setModified(date);
        genealogyDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());

        // 保存家族
        int affectedRows = 0;
        affectedRows += this.baseMapper.insert(genealogyDO);
        // 保存家族成员概况默认信息：总人数、男性人数、女性人数、健在人数、已逝人数均为 0
        this.genealogyProfileService.create(genealogyDO.getId());
        // 修改用户已经关联家族的默认家族状态为否
        this.genealogyUserService.updateGenealogiesStatusOfUser(userId, DefaultStatusEnum.NO.getCode());
        // 关联用户当前新建的家族，并将其设为默认家族
        this.genealogyUserService.createUserDefaultGenealogy(userId, genealogyDO.getId());

        return affectedRows == 1;
    }

    @Override
    public List<GenealogyVO> listGenealogiesWithProfileOfUser(Long userId) {
        // 从家族-用户关联表中查询当前用户关联的所有家族
        List<GenealogyUserDO> genealogyUserDOList = this.genealogyUserService.listGenealogiesOfUser(userId);
        if (genealogyUserDOList == null || genealogyUserDOList.isEmpty()) {
            return null;
        }

        List<GenealogyVO> result = new ArrayList<>();
        // 遍历用户关联的所有家族，依次查询每个家族的成员概况概况信息、家族信息、家族管理员信息
        genealogyUserDOList.forEach(item -> {
            Long gid = item.getGenealogyId();
            GenealogyVO genealogyVO = new GenealogyVO();
            // 查询当前家族的成员概况信息
            GenealogyProfileDO overviewDO = this.genealogyProfileService.getByGenealogyId(gid);
            BeanUtils.copyProperties(overviewDO, genealogyVO);
            // 查询当前家族信息
            GenealogyDO genealogyDO = this.getById(gid);
            BeanUtils.copyProperties(genealogyDO, genealogyVO);
            // 查询家族创建者信息
            PeopleVO genealogyCreator = this.peopleService.getByUserGenealogyId(genealogyDO.getCreatorUserId(), item.getGenealogyId());
            PeopleCardVO genealogyCreatorVO = new PeopleCardVO();
            BeanUtils.copyProperties(genealogyCreator, genealogyCreatorVO);
            genealogyVO.setCreator(genealogyCreatorVO);
            // 查询家族管理员列表信息
            List<PeopleCardVO> genealogyAdmins = this.genealogyAdminService.listAdminsOfGenealogy(item.getGenealogyId());
            genealogyVO.setAdmins(genealogyAdmins);
            // 设置当前用户家族的默认状态
            genealogyVO.setDefaultGenealogy(item.getDefaultGenealogy());
            result.add(genealogyVO);
        });

        return result;
    }

    @Override
    public boolean editGenealogy(GenealogyQuery genealogyQuery) {
        GenealogyDO genealogyDO = new GenealogyDO();
        BeanUtils.copyProperties(genealogyQuery, genealogyDO);
        return this.baseMapper.updateById(genealogyDO) == 1;
    }

}
