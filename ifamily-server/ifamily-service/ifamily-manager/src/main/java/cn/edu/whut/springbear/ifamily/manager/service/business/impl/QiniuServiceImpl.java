package cn.edu.whut.springbear.ifamily.manager.service.business.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.common.exception.SystemServiceException;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.manager.enumerate.ImageTypeEnum;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.QiniuLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.QiniuLogService;
import cn.edu.whut.springbear.ifamily.manager.service.business.QiniuService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    @Override
    public Map<String, String> imgToken(String suffix, Integer type) {
        // 图片类型：[1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]家族大事 [6]动态图片
        String imgType;
        switch (type) {
            case 1:
                imgType = ImageTypeEnum.USER_AVATAR.getDirectory();
                break;
            case 2:
                imgType = ImageTypeEnum.CLAN_COVER.getDirectory();
                break;
            case 3:
                imgType = ImageTypeEnum.PEOPLE_PORTRAIT.getDirectory();
                break;
            case 4:
                imgType = ImageTypeEnum.FAMILY_ALBUM.getDirectory();
                break;
            case 5:
                imgType = ImageTypeEnum.MEMORABILIA.getDirectory();
                break;
            case 6:
                imgType = ImageTypeEnum.MOMENT.getDirectory();
                break;
            default:
                throw new IncorrectConditionException("图片类型：[1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]家族大事 [6]动态图片");
        }

        // 自定义文件上传名，例：img/avatar/23/03/27/a5c8a5e8-df2b-4706-bea4-08d0939410e3.png
        String key = "img/" + imgType + "/" + DateUtil.format(new Date(), "yyyy/MM/dd") + "/" + IdUtil.randomUUID() + suffix;

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
