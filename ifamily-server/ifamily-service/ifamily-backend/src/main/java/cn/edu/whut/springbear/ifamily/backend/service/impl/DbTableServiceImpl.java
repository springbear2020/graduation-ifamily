package cn.edu.whut.springbear.ifamily.backend.service.impl;

import cn.edu.whut.springbear.ifamily.backend.mapper.DbTableMapper;
import cn.edu.whut.springbear.ifamily.backend.pojo.bo.DbTableBO;
import cn.edu.whut.springbear.ifamily.backend.pojo.po.DbTableDO;
import cn.edu.whut.springbear.ifamily.backend.service.DbTableService;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/05/27 07:11
 */
@RequiredArgsConstructor
@Service
public class DbTableServiceImpl extends ServiceImpl<DbTableMapper, DbTableDO> implements DbTableService {

    @Value("${mysql.root.username}")
    private String mysqlRootUsername;
    @Value("${mysql.root.password}")
    private String mysqlRootPassword;

    private final ResourceLoader resourceLoader;

    @Override
    public List<DbTableBO> dbTables() {
        List<DbTableDO> dbTableDOList = this.list();
        if (CollUtil.isEmpty(dbTableDOList)) {
            return null;
        }

        // 使用 0 作为标识符，避免 parentId == null 导致分组失败
        final long identifier = 0L;
        Map<Long, List<DbTableDO>> groupedMap = dbTableDOList.stream()
                .collect(Collectors.groupingBy(dbTableDO -> {
                    Long parentId = dbTableDO.getParentId();
                    return parentId != null ? parentId : identifier;
                }));

        List<DbTableBO> resultList = new ArrayList<>();
        // iterator all databases
        groupedMap.get(identifier).forEach(database -> {
            DbTableBO dbTableBO = new DbTableBO();
            BeanUtils.copyProperties(database, dbTableBO);
            // set tables of current database
            dbTableBO.setTables(groupedMap.get(database.getId()));
            resultList.add(dbTableBO);
        });

        return resultList;
    }

    @Override
    public String backup(Long id) {
        DbTableDO dbTableDO = this.getById(id);
        if (dbTableDO == null) {
            return null;
        }

        if (dbTableDO.getParentId() == null) {
            // backup the database
            Resource resource = this.resourceLoader.getResource("classpath:backup\\db");
            try {
                String fileSavePath = resource.getFile().getAbsolutePath() + "\\" + dbTableDO.getName() + ".sql";
                String cmd = "mysqldump -u" + mysqlRootUsername + " -p" + mysqlRootPassword + " -B " + dbTableDO.getName() + " > " + fileSavePath;
                Process exec = Runtime.getRuntime().exec("cmd /c" + cmd);
                // whether the command executed successfully
                return exec.waitFor() == 0 ? fileSavePath : null;
            } catch (IOException | InterruptedException e) {
                throw new IncorrectConditionException("请求备份 `" + dbTableDO.getName() + "` 数据库失败");
            }
        }

        throw new IncorrectConditionException("表数据导出功能待实现...");
    }

}
