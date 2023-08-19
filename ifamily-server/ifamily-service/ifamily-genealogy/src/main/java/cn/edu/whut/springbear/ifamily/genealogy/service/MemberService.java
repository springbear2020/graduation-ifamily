package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeNodeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemberQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/04/03 17:48
 */
public interface MemberService extends IService<PeopleDO> {

    /**
     * 通过用户 ID 列表查询和家族 ID 查询家族成员列表信息
     *
     * @param userIds     用户 ID 列表
     * @param genealogyId 家族 ID
     * @return 家族成员列表
     */
    List<PeopleDO> listMemberInBatchUserIds(List<Long> userIds, Long genealogyId);

    /**
     * 查询家族成员树谱人员列表
     *
     * @param genealogyId 家族 ID
     * @return 家族人员树谱结构数据
     */
    MemberTreeNodeBO memberTree(Long genealogyId);

    /**
     * 查询家族成员世代分组列表，可根据家族人员姓名、性别以及生逝状态进行过滤查询
     *
     * @param memberQuery 查询条件：必须包含家族 ID，人员姓名、性别和生逝状态可选
     * @return 返回的 Map 中包含了家族世代列表和每个列表下的家族人员
     */
    Map<String, Object> listGenerationMembers(MemberQuery memberQuery);

    /**
     * 根据姓名模糊查询家族人员列表
     */
    List<PeopleCardVO> listMembersByName(String name, Long genealogyId);

}
