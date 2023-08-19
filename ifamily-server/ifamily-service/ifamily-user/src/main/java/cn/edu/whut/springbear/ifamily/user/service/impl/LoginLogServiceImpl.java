package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.user.mapper.LoginLogMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.LoginLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.LoginLogVO;
import cn.edu.whut.springbear.ifamily.user.service.LoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@AllArgsConstructor
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
        loginLogDO.setUserId(userId);
        return this.save(loginLogDO);
    }

    @Override
    public List<LoginLogVO> page(PageQuery pageQuery, Long userId) {
        // 查询用户登录记录分页数据
        QueryWrapper<LoginLogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("login_datetime");
        Page<LoginLogDO> pageData = new Page<>(pageQuery.getCurrent(), pageQuery.getSize());
        pageData = this.baseMapper.selectPage(pageData, queryWrapper);
        if (pageData == null || pageData.getRecords().isEmpty()) {
            return null;
        }

        // 将用户登录数据 DO 封装为 VO
        List<LoginLogDO> records = pageData.getRecords();
        List<LoginLogVO> resultList = new ArrayList<>();
        records.forEach(item -> {
            LoginLogVO loginLogVO = new LoginLogVO();
            BeanUtils.copyProperties(item, loginLogVO);
            resultList.add(loginLogVO);
        });
        return resultList;
    }

}
