package cn.edu.whut.springbear.ifamily.manager.service;

import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeSendLogDO;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 15:29
 */
@SpringBootTest
class CodeSendLogServiceTest {

    @Autowired
    private CodeSendLogService codeSendLogService;

    @Test
    void listLogsOnSpecifiedDate() {
        Date date = DateUtil.parse("2023-03-26", "yyyy-MM-dd");
        List<CodeSendLogDO> codeSendLogDOS = codeSendLogService.listLogsOnSpecifiedDate("springbear2020@163.com", date);
        codeSendLogDOS.forEach(System.out::println);
    }

}