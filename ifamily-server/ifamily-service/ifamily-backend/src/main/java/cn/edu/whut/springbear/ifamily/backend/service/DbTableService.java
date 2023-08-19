package cn.edu.whut.springbear.ifamily.backend.service;

import cn.edu.whut.springbear.ifamily.backend.pojo.bo.DbTableBO;
import cn.edu.whut.springbear.ifamily.backend.pojo.po.DbTableDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/27 07:10
 */
public interface DbTableService extends IService<DbTableDO> {

    /**
     * 查询系统所有数据库以及数据库下的所有表
     *
     * @return 数据库和表
     */
    List<DbTableBO> dbTables();

    /**
     * 备份数据库脚本或导出数据库表
     *
     * @param id 数据库 ID 或表 ID
     * @return 成功返回文件路径，失败返回 null
     */
    String backup(Long id);

}
