package cn.edu.whut.springbear.ifamily.manager.service.impl;


import cn.edu.whut.springbear.ifamily.common.constant.RedisConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.common.enumerate.SuccessStatusEnum;
import cn.edu.whut.springbear.ifamily.common.exception.SystemServiceException;
import cn.edu.whut.springbear.ifamily.manager.enumerate.CodeTypeEnum;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeSendLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.CodeSendLogService;
import cn.edu.whut.springbear.ifamily.manager.service.CodeService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 09:19
 */
@Service
public class CodeServiceImpl implements CodeService {

    private static final Integer CODE_LENGTH = 6;
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CodeSendLogService codeSendLogService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    TemplateEngine templateEngine;

    @Override
    public boolean sendEmailCode(String email) {
        // TODO 限制今日验证码发送次数

        String code = RandomUtil.randomNumbers(CODE_LENGTH);
        // 将验证码内容注入到邮件 HTML 模板中，而后读取模板文件，将其内容解析为字符串
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("codeList", Arrays.asList(code.split("")));
        String emailContent = templateEngine.process("email-code-template", context);

        // 验证码发送记录
        CodeSendLogDO codeSendLogDO = new CodeSendLogDO(null, email, code, CodeTypeEnum.EMAIL.getCode(),
                SuccessStatusEnum.FAILED.getCode(), new Date(), new Date(), DeleteStatusEnum.UNDELETED.getCode());
        try {
            // 尝试发送邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender, "百家谱");
            helper.setTo(email);
            helper.setSubject("【百家谱】邮箱验证码身份认证");
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
            // 邮件发送成功，更新发送记录状态为成功
            codeSendLogDO.setStatus(SuccessStatusEnum.SUCCESS.getCode());
        } catch (MessagingException | UnsupportedEncodingException e) {
            // [503]服务不可用 -> 系统繁忙，请稍后重试
            throw new SystemServiceException(e.getMessage());
        } finally {
            codeSendLogService.save(codeSendLogDO);
        }

        // 将验证码存入 Redis 中，并设置有效期。key = "code:" + email
        String key = RedisConstants.VERIFY_CODE_PREFIX + email;
        redisTemplate.opsForValue().set(key, code, RedisConstants.CODE_TIMEOUT, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public boolean sendSmsCode(String phone) {
        // TODO 手机验证码发送功能待实现
        return true;
    }

}
