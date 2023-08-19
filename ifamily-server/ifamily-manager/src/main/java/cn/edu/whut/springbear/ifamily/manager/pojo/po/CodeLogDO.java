package cn.edu.whut.springbear.ifamily.manager.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 2023-03-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("code_log")
public class CodeLogDO extends AbstractBaseDO {

    private static final long serialVersionUID = 7000871776368497458L;

    /**
     * 收件人：邮箱或手机
     */
    private String receiver;

    /**
     * 验证码
     */
    private String code;

    /**
     * 类型：[0]邮箱验证码 [1]短信验证码
     */
    private Integer type;

    /**
     * 是否发送成功：[0]否 [1]是
     */
    private Integer success;

}
