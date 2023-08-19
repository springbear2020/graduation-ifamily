package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:01
 */
public interface PeopleService extends IService<PeopleDO> {

    /**
     * 查询指定用户在指定家族中的人员信息
     *
     * @param userId      用户 ID
     * @param genealogyId 家族 ID
     * @return 家族人员信息
     */
    PeopleVO getUserPeople(Long userId, Long genealogyId);

    /**
     * 保存用户在家族中的人员信息
     *
     * @param userPeople  用户在默认家族中的资料
     * @param userId      用户 ID
     * @param genealogyId 用户家族 ID
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean createUserPeople(PeopleQuery userPeople, Long userId, Long genealogyId);

    /**
     * 查询家族人员详细信息，包括个人详细信息以及家庭关系
     */
    PeopleDetailsBO getPeopleDetails(Long genealogyId, Long peopleId, Long operatorUserId);

    /**
     * 根据家族 ID 和人员 ID 查询家族人员
     *
     * @param genealogyId 家族 ID
     * @param peopleId    人员 ID
     * @return 家族人员信息
     */
    PeopleVO getGenealogyPeople(Long genealogyId, Long peopleId);

    /**
     * 根据家族 ID 和人员 ID 更新家族人员
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean editGenealogyPeople(Long genealogyId, PeopleQuery peopleQuery, Long operatorUserId);

    /**
     * 根据家族  ID 和人员 ID 移除家族人员
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean removeGenealogyPeople(Long genealogyId, Long peopleId, Long operatorUserId);

    /**
     * 保存家族人员父亲
     *
     * @param genealogyId    家族 ID
     * @param mePeopleId     我的家族人员 ID
     * @param fatherInfo     父亲信息
     * @param operatorUserId 操作用户 ID
     * @return [true]保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean saveGenealogyPeopleFather(Long genealogyId, Long mePeopleId, PeopleQuery fatherInfo, Long operatorUserId);

    /**
     * 保存家族人员母亲
     *
     * @param genealogyId    家族 ID
     * @param mePeopleId     我的家族人员 ID
     * @param motherInfo     母亲信息
     * @param operatorUserId 操作用户 ID
     * @return [true]保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean saveGenealogyPeopleMother(Long genealogyId, Long mePeopleId, PeopleQuery motherInfo, Long operatorUserId);

    /**
     * 保存家族人员妻子
     *
     * @param genealogyId    家族 ID
     * @param mePeopleId     我的家族人员 ID
     * @param wifeInfo       妻子信息
     * @param operatorUserId 操作用户 ID
     * @return [true]保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean saveGenealogyPeopleWife(Long genealogyId, Long mePeopleId, PeopleQuery wifeInfo, Long operatorUserId);

    /**
     * 保存家族人员孩子
     *
     * @param genealogyId    家族 ID
     * @param mePeopleId     我的家族人员 ID
     * @param childInfo      孩子信息
     * @param operatorUserId 操作用户 ID
     * @return [true]保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean saveGenealogyPeopleChild(Long genealogyId, Long mePeopleId, PeopleQuery childInfo, Long operatorUserId);

    /**
     * 保存家族人员同胞
     *
     * @param genealogyId    家族 ID
     * @param mePeopleId     我的家族人员 ID
     * @param compatriotInfo 同胞信息
     * @param operatorUserId 操作用户 ID
     * @return [true]保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean saveGenealogyPeopleCompatriot(Long genealogyId, Long mePeopleId, PeopleQuery compatriotInfo, Long operatorUserId);
}
