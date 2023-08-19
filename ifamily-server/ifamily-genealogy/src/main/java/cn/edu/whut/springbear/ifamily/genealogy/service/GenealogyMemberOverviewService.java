package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyMemberOverviewDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:02
 */
public interface GenealogyMemberOverviewService extends IService<GenealogyMemberOverviewDO> {

    /**
     * 保存默认家族成员概况信息：所有人数均为 0
     */
    boolean createDefault(Long genealogyId);

    /**
     * 通过家族 ID 查询家族成员概况信息
     */
    GenealogyMemberOverviewDO getByGenealogyId(Long gid);

}
