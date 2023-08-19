package cn.edu.whut.springbear.ifamily.backend.service;

import cn.edu.whut.springbear.ifamily.backend.pojo.bo.DbTableBO;
import cn.edu.whut.springbear.ifamily.backend.pojo.po.DbTableDO;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author Spring-_-Bear
 * @since 23/05/27 07:11
 */
@SpringBootTest
public class DbTableServiceTest {

    @Autowired
    private DbTableService dbTableService;

    @Test
    public void dbTables() {
        List<DbTableBO> dbTableBOS = this.dbTableService.dbTables();
        dbTableBOS.forEach(System.out::println);
    }

    @Test
    public void backup() {
        this.dbTableService.backup(1662241553642356738L);
    }

    @Test
    void initAll() {
        this.initAcl();
        this.initBackend();
        this.initGenealogy();
        this.initManager();
        this.initSocial();
        this.initUser();
    }

    void initAcl() {
        // save database
        DbTableDO db = new DbTableDO();
        db.setName("ifamily_acl");
        db.setDescription("权限访问控制数据库");
        Date date = new Date();
        db.setCreated(date);
        db.setModified(date);
        db.setDeleted(AssertEnum.NO.getCode());
        this.dbTableService.save(db);
        Long dbId = db.getId();

        String[][] tables = new String[][]{
                {"admin_role_relation", "管理员 - 角色关系表"},
                {"menu", "前台路由菜单表"},
                {"permission", "后台接口权限表"},
                {"role", "系统角色表"},
                {"role_menu_relation", "角色 - 菜单关系表"},
                {"role_permission_relation", "角色 - 权限关系表"},
                {"user_role_relation", "用户 - 角色关系表"},
        };

        // save tables of database
        for (String[] table : tables) {
            db.setId(null);
            db.setName(table[0]);
            db.setDescription(table[1]);
            db.setParentId(dbId);
            this.dbTableService.save(db);
        }
    }

    void initBackend() {
        // save database
        DbTableDO db = new DbTableDO();
        db.setName("ifamily_backend");
        db.setDescription("后台信息管理数据库");
        Date date = new Date();
        db.setCreated(date);
        db.setModified(date);
        db.setDeleted(AssertEnum.NO.getCode());
        this.dbTableService.save(db);
        Long dbId = db.getId();

        String[][] tables = new String[][]{
                {"admin", "管理员基本信息表"},
                {"admin_login_log", "管理员登录记录表"},
                {"db_table", "百家谱系统数据库表"},
        };

        // save tables of database
        for (String[] table : tables) {
            db.setId(null);
            db.setName(table[0]);
            db.setDescription(table[1]);
            db.setParentId(dbId);
            this.dbTableService.save(db);
        }
    }

    void initGenealogy() {
        // save database
        DbTableDO db = new DbTableDO();
        db.setName("ifamily_genealogy");
        db.setDescription("家族数据库");
        Date date = new Date();
        db.setCreated(date);
        db.setModified(date);
        db.setDeleted(AssertEnum.NO.getCode());
        this.dbTableService.save(db);
        Long dbId = db.getId();

        String[][] tables = new String[][]{
                {"genealogy", "家族基本信息表"},
                {"genealogy_memorabilia", "家族大事表"},
                {"genealogy_notice", "家族公告表"},
                {"genealogy_people", "家族人员信息表"},
                {"genealogy_photo", "家族相册表"},
                {"genealogy_profile", "家族成员概况表"},
                {"genealogy_revision_log", "家族修谱日志表"},
                {"genealogy_visitor_log", "家族访问记录表"},
                {"user_genealogy_relation", "用户 - 家族关系表"},
        };

        // save tables of database
        for (String[] table : tables) {
            db.setId(null);
            db.setName(table[0]);
            db.setDescription(table[1]);
            db.setParentId(dbId);
            this.dbTableService.save(db);
        }
    }

    void initManager() {
        // save database
        DbTableDO db = new DbTableDO();
        db.setName("ifamily_manager");
        db.setDescription("第三方服务数据库");
        Date date = new Date();
        db.setCreated(date);
        db.setModified(date);
        db.setDeleted(AssertEnum.NO.getCode());
        this.dbTableService.save(db);
        Long dbId = db.getId();

        String[][] tables = new String[][]{
                {"code_send_log", "验证码发送记录表"},
                {"qiniu_token_log", "七牛云令牌获取记录表"},
        };

        // save tables of database
        for (String[] table : tables) {
            db.setId(null);
            db.setName(table[0]);
            db.setDescription(table[1]);
            db.setParentId(dbId);
            this.dbTableService.save(db);
        }
    }

    void initSocial() {
        // save database
        DbTableDO db = new DbTableDO();
        db.setName("ifamily_social");
        db.setDescription("用户社交数据库");
        Date date = new Date();
        db.setCreated(date);
        db.setModified(date);
        db.setDeleted(AssertEnum.NO.getCode());
        this.dbTableService.save(db);
        Long dbId = db.getId();

        String[][] tables = new String[][]{
                {"moment", "动态基本信息表"},
                {"moment_comment", "动态评论内容表"},
                {"moment_like", "动态点赞用户表"},
                {"moment_photo", "动态图片表"},
        };

        // save tables of database
        for (String[] table : tables) {
            db.setId(null);
            db.setName(table[0]);
            db.setDescription(table[1]);
            db.setParentId(dbId);
            this.dbTableService.save(db);
        }
    }

    void initUser() {
        // save database
        DbTableDO db = new DbTableDO();
        db.setName("ifamily_user");
        db.setDescription("用户数据库");
        Date date = new Date();
        db.setCreated(date);
        db.setModified(date);
        db.setDeleted(AssertEnum.NO.getCode());
        this.dbTableService.save(db);
        Long dbId = db.getId();

        String[][] tables = new String[][]{
                {"user", "用户基本信息表"},
                {"user_login_log", "用户登录记录表"},
                {"username_update_log", "用户用户名修改记录表"},
        };

        // save tables of database
        for (String[] table : tables) {
            db.setId(null);
            db.setName(table[0]);
            db.setDescription(table[1]);
            db.setParentId(dbId);
            this.dbTableService.save(db);
        }
    }

}
