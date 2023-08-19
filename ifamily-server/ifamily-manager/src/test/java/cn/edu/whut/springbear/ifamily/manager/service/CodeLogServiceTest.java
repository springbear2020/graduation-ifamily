package cn.edu.whut.springbear.ifamily.manager.service;

import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeLogDO;
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
class CodeLogServiceTest {

    @Autowired
    private CodeLogService codeLogService;

    @Test
    void listLogsOnSpecifiedDate() {
        Date date = DateUtil.parse("2023-03-26", "yyyy-MM-dd");
        List<CodeLogDO> codeLogDOS = codeLogService.listByDateAndReceiver("springbear2020@163.com", date);
        codeLogDOS.forEach(System.out::println);
    }

}