package cn.edu.whut.springbear.ifamily.manager.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.common.enumerate.SuccessStatusEnum;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.QiniuTokenLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.QiniuService;
import cn.edu.whut.springbear.ifamily.manager.service.QiniuTokenLogService;
import cn.edu.whut.springbear.ifamily.manager.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 16:19
 */
@Service
@Slf4j
public class QiniuServiceImpl implements QiniuService {

    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.cdn}")
    private String cdn;

    @Autowired
    private QiniuTokenLogService qiniuTokenLogService;
    @Autowired
    private SecurityUserService securityUserService;

    @Override
    public Map<String, String> imgToken(String key) {
        StringMap policy = new StringMap();
        // 限制文件上传类型为图片
        policy.put("mimeLimit", "image/*");
        // 限制文件上传大小 5M
        policy.put("fsizeLimit", 5242880);
        // 只能上传到 bucket 空间
        policy.put("scope", bucket + key);

        // 七牛云 token 获取记录
        UserDO currentUser = securityUserService.getCurrentUser();
        QiniuTokenLogDO qiniuTokenLogDO = this.qiniuTokenLogDO(key, currentUser.getId());

        String token;
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            // token 有效期 60 分钟
            token = auth.uploadToken(bucket, key, 3600, policy);
            qiniuTokenLogDO.setToken(token);
            qiniuTokenLogDO.setStatus(SuccessStatusEnum.SUCCESS.getCode());
        } catch (Exception e) {
            qiniuTokenLogDO.setToken("");
            log.error(e.getMessage());
            return null;
        } finally {
            // 保存 token 获取记录
            qiniuTokenLogService.save(qiniuTokenLogDO);
        }

        // 返回客户端必要数据：key - token - cdn
        Map<String, String> map = new HashMap<>(3);
        map.put("key", key);
        map.put("token", token);
        map.put("cdn", cdn);
        return map;
    }

    private QiniuTokenLogDO qiniuTokenLogDO(String key, Long userId) {
        Date date = new Date();
        QiniuTokenLogDO qiniuTokenLogDO = new QiniuTokenLogDO();
        qiniuTokenLogDO.setBucket(bucket);
        qiniuTokenLogDO.setFileKey(key);
        qiniuTokenLogDO.setCdn(cdn);
        qiniuTokenLogDO.setToken(null);
        qiniuTokenLogDO.setStatus(SuccessStatusEnum.FAILED.getCode());
        qiniuTokenLogDO.setCreated(date);
        qiniuTokenLogDO.setModified(date);
        qiniuTokenLogDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        qiniuTokenLogDO.setUserId(userId);
        return qiniuTokenLogDO;
    }

}
