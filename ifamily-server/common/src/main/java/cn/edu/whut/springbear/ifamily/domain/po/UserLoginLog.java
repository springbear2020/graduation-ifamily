package cn.edu.whut.springbear.ifamily.domain.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginLog extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = -6298765208719067231L;

    /**
     * IP 地址
     */
    @ApiModelProperty("IP 地址")
    private String ip;

    /**
     * IP 归属地
     */
    @ApiModelProperty("IP 归属地")
    private String location;

    /**
     * 登录设备名称
     */
    @ApiModelProperty("登录设备名称")
    private String device;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDatetime;

    /**
     * 用户 ID
     */
    @ApiModelProperty("用户 ID")
    private Long userId;
}
