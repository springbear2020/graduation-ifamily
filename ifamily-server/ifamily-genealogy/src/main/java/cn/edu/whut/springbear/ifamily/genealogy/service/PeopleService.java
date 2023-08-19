package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyMemberTreeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:01
 */
public interface PeopleService extends IService<PeopleDO> {

    /**
     * 查询家族人员世代分组列表
     *
     * @param genealogyId 家族 ID
     * @return 返回 Map 中包含了家族世代列表和每个列表下的家族人员
     */
    Object listGenerationMembersOfGenealogy(Long genealogyId);

    /**
     * 查询家族下的世代集合，升序排列
     *
     * @param genealogyId 家族 ID
     * @return 家族世代集合
     */
    List<Integer> listGenerationNumsOfGenealogy(Long genealogyId);

    /**
     * 查询家族世代下的所有人员
     *
     * @param genealogyId 家族 ID
     * @param generation  家族世代
     * @return 家族人员列表
     */
    List<PeopleDO> listPeopleOfGeneration(Long genealogyId, Integer generation);

    /**
     * 查询家族成员树谱人员列表
     *
     * @param genealogyId 家族 ID
     * @return 家族人员树谱结构数据
     */
    GenealogyMemberTreeBO listMemberTree(Long genealogyId);

    /**
     * 根据用户 ID 和家族 ID 查询家族人员信息
     */
    PeopleVO getByUserGenealogyId(Long userId, Long genealogyId);

}