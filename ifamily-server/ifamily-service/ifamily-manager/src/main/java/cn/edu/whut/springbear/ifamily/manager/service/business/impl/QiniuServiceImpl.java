package cn.edu.whut.springbear.ifamily.manager.service.business.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.SystemServiceException;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.QiniuLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.QiniuLogService;
import cn.edu.whut.springbear.ifamily.manager.service.business.QiniuService;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 16:19
 */
@Service
public class QiniuServiceImpl implements QiniuService {

    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.cdn}")
    private String cdn;

    private final QiniuLogService qiniuLogService;
    private final HttpServletRequest httpServletRequest;

    public QiniuServiceImpl(QiniuLogService qiniuLogService, HttpServletRequest httpServletRequest) {
        this.qiniuLogService = qiniuLogService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public Map<String, String> imgToken(String key) {
        StringMap policy = new StringMap();
        // 限制文件上传类型为图片
        policy.put("mimeLimit", "image/*");
        // 限制文件上传大小 5M
        policy.put("fsizeLimit", 5 * 1024 * 1024);
        // 只能上传到 bucket 空间
        policy.put("scope", bucket + key);

        // 七牛云 token 获取记录
        QiniuLogDO qiniuLogDO = new QiniuLogDO();
        qiniuLogDO.setBucket(bucket);
        qiniuLogDO.setFileKey(key);
        qiniuLogDO.setCdn(cdn);
        qiniuLogDO.setToken("");
        qiniuLogDO.setSuccess(AssertEnum.NO.getCode());
        Date date = new Date();
        qiniuLogDO.setCreated(date);
        qiniuLogDO.setModified(date);
        qiniuLogDO.setDeleted(AssertEnum.NO.getCode());
        // 从请求头中解析已认证的用户信息
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        qiniuLogDO.setUserId(userDTO.getId());

        String token;
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            // token 有效期 60 分钟
            token = auth.uploadToken(bucket, key, 60 * 60, policy);
            qiniuLogDO.setToken(token);
            qiniuLogDO.setSuccess(AssertEnum.YES.getCode());
        } catch (Exception e) {
            throw new SystemServiceException(e.getMessage());
        } finally {
            this.qiniuLogService.save(qiniuLogDO);
        }

        // 返回客户端必要数据：key, token, cdn
        Map<String, String> map = new HashMap<>(3);
        map.put("key", key);
        map.put("token", token);
        map.put("cdn", cdn);
        return map;
    }

}
