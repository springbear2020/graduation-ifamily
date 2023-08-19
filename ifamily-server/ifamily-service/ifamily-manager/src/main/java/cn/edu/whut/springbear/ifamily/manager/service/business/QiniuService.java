package cn.edu.whut.springbear.ifamily.manager.service.business;

import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 16:19
 */
public interface QiniuService {

    /**
     * 从七牛云平台获取图片文件上传 token
     *
     * @param suffix 源文件后缀名
     * @param type   [1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]人物事迹
     * @return Map: 键中包含 token, key, cdn
     */
    Map<String, String> imgToken(String suffix, Integer type);

}
