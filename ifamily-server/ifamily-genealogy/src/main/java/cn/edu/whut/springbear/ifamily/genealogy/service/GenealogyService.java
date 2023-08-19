package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyMemberVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:57
 */
public interface GenealogyService extends IService<GenealogyDO> {

    /**
     * 创建家族
     */
    boolean create(GenealogyQuery genealogyQuery);

    /**
     * 查询用户关联的所有家族，包含家族成员概况信息
     */
    List<GenealogyMemberVO> listGenealogiesOfUser();

}
