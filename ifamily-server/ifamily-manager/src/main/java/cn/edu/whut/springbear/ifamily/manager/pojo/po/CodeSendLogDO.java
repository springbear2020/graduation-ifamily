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
@TableName("code_send_log")
public class CodeSendLogDO extends AbstractBaseDO {

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
     * 发送状态：[0]成功 [1]失败
     */
    private Integer status;

}
