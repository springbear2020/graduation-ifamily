package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyAdminDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyAdminVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 19:25
 */
public interface GenealogyAdminService extends IService<GenealogyAdminDO> {

    /**
     * 查询家族管理员列表
     */
    List<GenealogyAdminVO> listAdminsOfGenealogy(Long genealogyId);

    /**
     * 查询家族管理员的 ID 集合
     */
    List<Long> listAdminUserIdsOfGenealogy(Long genealogyId);

}
