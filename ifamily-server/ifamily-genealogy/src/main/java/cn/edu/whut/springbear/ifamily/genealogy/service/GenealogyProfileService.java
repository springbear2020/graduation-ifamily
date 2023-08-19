package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyProfileDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:02
 */
public interface GenealogyProfileService extends IService<GenealogyProfileDO> {

    /**
     * 保存家族成员默认概况信息：所有人数均为 0
     */
    boolean create(Long genealogyId);

    /**
     * 通过家族 ID 查询家族成员概况信息
     */
    GenealogyProfileDO getByGenealogyId(Long genealogyId);

    /**
     * 将家族总人数 +1，根据性别男性或女性人数 +1，根据生逝状态健在或已逝人数 +1
     *
     * @param genealogyId 家族 ID
     * @param gender      性别：[0]男 [1]女
     * @param alive       生逝状态：[true]健在 [false]已逝
     * @return [1]更新成功
     */
    Integer genealogyPeopleProfileIncreaseOne(Long genealogyId, Integer gender, Boolean alive);

}
