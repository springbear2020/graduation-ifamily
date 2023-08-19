package cn.edu.whut.springbear.ifamily.manager.service;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 09:13
 */
public interface CodeService {

    /**
     * 发送邮件
     *
     * @param email 收件人邮件地址
     * @return [true]投递成功
     */
    boolean sendEmailCode(String email);

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @return [true]发送成功
     */
    boolean sendSmsCode(String phone);

}
