package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/03 17:48
 */
public interface MemberService extends IService<PeopleDO> {

    /**
     * 查询家族人员世代分组列表
     *
     * @param genealogyId 家族 ID
     * @return 返回 Map 中包含了家族世代列表和每个列表下的家族人员
     */
    Object listGenerationMembersOfGenealogy(Long genealogyId);

    /**
     * 查询家族成员树谱人员列表
     *
     * @param genealogyId 家族 ID
     * @return 家族人员树谱结构数据
     */
    MemberTreeBO listGenealogyMemberTree(Long genealogyId);

    /**
     * 根据姓名查询家族人员列表
     */
    List<PeopleCardVO> listPeopleByNameOfGenealogy(String name, Long genealogyId);

}
