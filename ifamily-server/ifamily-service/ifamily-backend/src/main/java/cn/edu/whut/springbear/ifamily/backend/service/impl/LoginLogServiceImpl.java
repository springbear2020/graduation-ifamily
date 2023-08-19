package cn.edu.whut.springbear.ifamily.backend.service.impl;

import cn.edu.whut.springbear.ifamily.backend.mapper.LoginLogMapper;
import cn.edu.whut.springbear.ifamily.backend.pojo.po.LoginLogDO;
import cn.edu.whut.springbear.ifamily.backend.service.LoginLogService;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/05/08 11:27
 */
@RequiredArgsConstructor
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO> implements LoginLogService {

    private final HttpServletRequest request;

    @Override
    public boolean create(Long userId) {
        LoginLogDO loginLogDO = new LoginLogDO();
        // 从请求头中解析 IP 地址
        String ipAddress = WebUtils.getRequestIp(request);
        loginLogDO.setIp(ipAddress);
        // 解析 IP 归属地：先使用百度地图 API，解析失败使用淘宝公共 API 解析
        String location = WebUtils.baiduParse(ipAddress);
        location = "未知地点".equals(location) ? WebUtils.taobaoParse(ipAddress) : location;
        loginLogDO.setLocation(location);
        // 从请求头中获取用户设备信息
        String device = WebUtils.userAgent(request);
        loginLogDO.setDevice(device);
        Date date = new Date();
        loginLogDO.setLoginDatetime(date);
        loginLogDO.setCreated(date);
        loginLogDO.setModified(date);
        loginLogDO.setDeleted(AssertEnum.NO.getCode());
        loginLogDO.setAdminId(userId);
        return this.save(loginLogDO);
    }

}
