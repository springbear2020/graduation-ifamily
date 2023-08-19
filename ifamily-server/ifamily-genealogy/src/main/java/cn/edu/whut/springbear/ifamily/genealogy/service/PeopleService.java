package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:01
 */
public interface PeopleService extends IService<PeopleDO> {

    /**
     * 根据用户 ID 和家族 ID 查询家族人员信息
     */
    PeopleVO getByUserGenealogyId(Long userId, Long genealogyId);

    /**
     * 查询家族人员详细信息，包括个人详细信息以及家庭关系
     */
    PeopleDetailsBO getWithDetails(Long peopleId);

    /**
     * 根据人员 ID 查询家族人员信息
     */
    PeopleVO get(Long peopleId);

    /**
     * 根据 ID 更新家族人员信息
     */
    boolean edit(PeopleQuery peopleQuery);

    /**
     * 添加父亲
     *
     * @param mePeopleId “我” 的家族 ID
     * @param fatherInfo 父亲的家族信息
     */
    boolean savePeopleFather(Long mePeopleId, PeopleQuery fatherInfo);

    /**
     * 添加母亲
     *
     * @param mePeopleId “我” 的家族 ID
     * @param motherInfo 母亲的家族信息
     */
    boolean savePeopleMother(Long mePeopleId, PeopleQuery motherInfo);

    /**
     * 添加妻子
     *
     * @param mePeopleId “我” 的家族 ID
     * @param wifeInfo   妻子的家族信息
     */
    boolean savePeopleWife(Long mePeopleId, PeopleQuery wifeInfo);

    /**
     * 添加孩子
     *
     * @param mePeopleId “我” 的家族 ID
     * @param childInfo  孩子的家族信息
     */
    boolean savePeopleChild(Long mePeopleId, PeopleQuery childInfo);

    /**
     * 添加同胞
     *
     * @param mePeopleId     “我” 的家族 ID
     * @param compatriotInfo 同胞的家族信息
     */
    boolean savePeopleCompatriot(Long mePeopleId, PeopleQuery compatriotInfo);

    /**
     * 保存用户在家族中的人员信息
     *
     * @param userPeople  用户在默认家族中的资料
     * @param userId      用户 ID
     * @param genealogyId 用户家族 ID
     */
    boolean create(PeopleQuery userPeople, Long userId, Long genealogyId);

    /**
     * 移除家族中的人员信息
     *
     * @param peopleId 人员 ID
     */
    boolean remove(Long peopleId);

}