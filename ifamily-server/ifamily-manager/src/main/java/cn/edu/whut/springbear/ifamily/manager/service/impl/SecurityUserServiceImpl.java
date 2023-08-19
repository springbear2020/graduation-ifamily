package cn.edu.whut.springbear.ifamily.manager.service.impl;

import cn.edu.whut.springbear.ifamily.common.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.common.exception.IllegalStatusException;
import cn.edu.whut.springbear.ifamily.manager.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.dto.SecurityUserDetailsDTO;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 10:32
 */
@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Override
    public UserDO getCurrentUser() {
        SecurityUserDetailsDTO userDetailsDTO = (SecurityUserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetailsDTO == null) {
            throw new IllegalStatusException(UserMessageConstants.UNAUTHORIZED);
        }
        return userDetailsDTO.getUser();
    }

}
