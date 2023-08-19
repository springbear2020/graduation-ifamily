package cn.edu.whut.springbear.ifamily.acl.pojo.bo;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.RoleUserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/07 09:15
 */
@Data
public class RoleBO implements Serializable {

    private static final long serialVersionUID = -7819231146085585717L;

    private RoleDO role;

    private List<Long> menuIds;

    private List<Long> permissionIds;

    private List<RoleUserVO> users;

    private List<RoleUserVO> admins;

}
