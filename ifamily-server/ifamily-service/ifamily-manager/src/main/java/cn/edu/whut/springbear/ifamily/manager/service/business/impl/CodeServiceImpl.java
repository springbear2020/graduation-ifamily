package cn.edu.whut.springbear.ifamily.manager.service.business.impl;

import cn.edu.whut.springbear.ifamily.common.constant.RedisConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.common.exception.SystemServiceException;
import cn.edu.whut.springbear.ifamily.manager.constants.CodeConstants;
import cn.edu.whut.springbear.ifamily.manager.enumerate.CodeTypeEnum;
import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeLogDO;
import cn.edu.whut.springbear.ifamily.manager.service.CodeLogService;
import cn.edu.whut.springbear.ifamily.manager.service.business.CodeService;
import cn.hutool.core.util.RandomUtil;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 09:19
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Value("${spring.mail.username}")
    private String sender;

    private final CodeLogService codeLogService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public CodeServiceImpl(CodeLogService codeLogService, TemplateEngine templateEngine, JavaMailSender javaMailSender, RedisTemplate<String, String> redisTemplate) {
        this.codeLogService = codeLogService;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean sendEmailCode(String email) {
        // 限制今日验证码发送次数
        List<CodeLogDO> codeLogDOList = this.codeLogService.listByDateAndReceiver(email, new Date());
        if (codeLogDOList != null && codeLogDOList.size() >= CodeConstants.MAX_SEND_TIMES) {
            throw new IncorrectConditionException("今日验证码发送次数已达上限 " + CodeConstants.MAX_SEND_TIMES + " 次");
        }

        String code = RandomUtil.randomNumbers(CodeConstants.CODE_LENGTH);
        // 将验证码内容注入到邮件 HTML 文件模板中，而后读取模板文件，将其内容解析为字符串
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("codeList", Arrays.asList(code.split("")));
        String emailContent = templateEngine.process("email-code-template", context);

        // 验证码发送记录
        CodeLogDO codeLogDO = new CodeLogDO();
        codeLogDO.setReceiver(email);
        codeLogDO.setCode(code);
        codeLogDO.setType(CodeTypeEnum.EMAIL_TYPE.getCode());
        codeLogDO.setSuccess(AssertEnum.NO.getCode());
        Date date = new Date();
        codeLogDO.setCreated(date);
        codeLogDO.setModified(date);
        codeLogDO.setDeleted(AssertEnum.NO.getCode());

        try {
            // 尝试发送邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender, "百家谱");
            helper.setTo(email);
            helper.setSubject("【百家谱】邮箱验证码身份认证");
            helper.setText(emailContent, true);
///            javaMailSender.send(mimeMessage);
            // 邮件发送成功，更新发送状态为成功
            codeLogDO.setSuccess(AssertEnum.YES.getCode());
        } catch (MessagingException | UnsupportedEncodingException e) {
            // [503]服务不可用 -> 系统繁忙，请稍后重试
            throw new SystemServiceException(e.getMessage());
        } finally {
            codeLogService.save(codeLogDO);
        }

        // 将验证码存入 Redis 中，并设置有效期
        String key = RedisConstants.CODE_PREFIX + email;
        redisTemplate.opsForValue().set(key, code, CodeConstants.CODE_VALIDITY_TIME, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public boolean sendPhoneCode(String phone) {
        // TODO 手机验证码发送功能待实现
        return false;
    }

}
