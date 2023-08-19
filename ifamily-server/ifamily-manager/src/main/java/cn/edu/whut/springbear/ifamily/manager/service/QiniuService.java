package cn.edu.whut.springbear.ifamily.manager.service;

import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 16:19
 */
public interface QiniuService {

    /**
     * 从七牛云平台获取图片文件上传 token
     *
     * @param key 文件名
     * @return Map: 键中包含 token, key, cdn
     */
    Map<String, String> imgToken(String key);

}
