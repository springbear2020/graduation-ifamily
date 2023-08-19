package cn.edu.whut.springbear.ifamily.manager.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 2023-03-23
 */
@Data
@TableName("code_send_log")
@NoArgsConstructor
@AllArgsConstructor
public class CodeSendLog implements Serializable {

    private static final long serialVersionUID = 7000871776368497458L;



    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 是否删除：[0]未删除 [1]已删除
     */
    private Integer isDeleted;

}
