package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:34
 */
public interface GenealogyUserService extends IService<GenealogyUserDO> {

    /**
     * 更新用户所关联的所有家族的默认状态
     *
     * @param userId 用户 ID
     * @param status 家族的新默认状态
     * @return 更新成功
     */
    boolean updateAllDefaultGenealogyOfUser(Long userId, Integer status);

    /**
     * 保存并关联用户默认家族
     */
    boolean createUserDefaultGenealogy(Long userId, Long genealogyId);

    /**
     * 查询用户关联的所有家族
     */
    List<GenealogyUserDO> listGenealogiesOfUser(Long userId);

    /**
     * 更新用户默认家族
     */
    boolean setDefaultGenealogyForUser(Long genealogyId);

}
