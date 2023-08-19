package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.user.mapper.UserLoginLogMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserLoginLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.LoginLogVO;
import cn.edu.whut.springbear.ifamily.user.service.UserLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDO> implements UserLoginLogService {

    @Autowired
    HttpServletRequest request;

    @Override
    public boolean create(Long userId) {
        UserLoginLogDO userLoginLogDO = new UserLoginLogDO();
        // 从请求头中解析 IP 地址
        String ipAddress = WebUtils.getRequestIp(request);
        userLoginLogDO.setIp(ipAddress);
        // 解析 IP 归属地，先使用百度地图 API，解析失败使用淘宝公共 API
///        String location = WebUtils.baiduParseIpLocation(ipAddress);
///        location = "未知地点".equals(location) ? WebUtils.taobaoParseIpLocation(ipAddress) : location;
        userLoginLogDO.setLocation("湖北省武汉市");
        // 从请求头中获取用户设备信息
        String device = WebUtils.userAgent(request);
        userLoginLogDO.setDevice(device);
        Date date = new Date();
        userLoginLogDO.setLoginDatetime(date);
        userLoginLogDO.setCreated(date);
        userLoginLogDO.setModified(date);
        userLoginLogDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        userLoginLogDO.setUserId(userId);
        return this.baseMapper.insert(userLoginLogDO) == 1;
    }

    @Override
    public List<LoginLogVO> page(PageQuery pageQuery, Long userId) {
        // 查询用户登录记录分页数据
        QueryWrapper<UserLoginLogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("login_datetime");
        Page<UserLoginLogDO> pageData = new Page<>(pageQuery.getCurrent(), pageQuery.getSize());
        pageData = this.baseMapper.selectPage(pageData, queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }

        // 将用户登录数据 DO 封装为 VO
        List<UserLoginLogDO> records = pageData.getRecords();
        List<LoginLogVO> resultList = new ArrayList<>();
        records.forEach(item -> {
            LoginLogVO loginLogVO = new LoginLogVO();
            BeanUtils.copyProperties(item, loginLogVO);
            resultList.add(loginLogVO);
        });
        return resultList;
    }

}
