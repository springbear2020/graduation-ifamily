package cn.edu.whut.springbear.ifamily.admin.service.impl;

import cn.edu.whut.springbear.ifamily.admin.mapper.AdminMapper;
import cn.edu.whut.springbear.ifamily.admin.pojo.po.AdminDO;
import cn.edu.whut.springbear.ifamily.admin.service.feign.AdminRoleFeignService;
import cn.edu.whut.springbear.ifamily.admin.service.AdminService;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:32
 */
@AllArgsConstructor
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminDO> implements AdminService {

    private final AdminRoleFeignService adminRoleFeignService;

    @Override
    public UserDTO loadAdminByUsername(String username) {
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        AdminDO adminDO = this.baseMapper.selectOne(queryWrapper);
        if (adminDO == null) {
            return null;
        }

        // DO -> DTO
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(adminDO, userDTO);

        // 查询管理员对应的角色列表
        List<String> adminRoleNames = this.adminRoleFeignService.listRoleNamesOfAdmin(userDTO.getId());
        userDTO.setRoles(adminRoleNames);

        return userDTO;
    }

}
