package cn.edu.whut.springbear.ifamily.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 19:48
 */
@Data
@Builder
public class SwaggerDTO {

    /**
     * API 文档生成基础包路径
     */
    private String apiBasePackage;
    /**
     * 是否要启用登录认证
     */
    private Boolean enableSecurity;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;

}
