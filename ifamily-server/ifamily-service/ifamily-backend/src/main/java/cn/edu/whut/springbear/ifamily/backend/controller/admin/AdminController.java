package cn.edu.whut.springbear.ifamily.backend.controller.admin;

import cn.edu.whut.springbear.ifamily.backend.pojo.bo.DbTableBO;
import cn.edu.whut.springbear.ifamily.backend.service.AdminService;
import cn.edu.whut.springbear.ifamily.backend.service.DbTableService;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageParam;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.AdminUserVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/10 21:18
 */
@RequiredArgsConstructor
@Api(tags = "管理员管理接口")
@RestController
@RequestMapping("/backend/admin")
public class AdminController {

    private final AdminService adminService;
    private final DbTableService dbTableService;

    @ApiOperation("查询管理员用户分页数据")
    @GetMapping("/page")
    public CommonResult<Object> pageData(@Validated PageParam pageQuery) {
        List<AdminUserVO> records = this.adminService.pageData(pageQuery);
        return CollUtil.isEmpty(records) ? CommonResult.failed("管理员用户无数据") : CommonResult.success(records);
    }

    @ApiOperation("查询系统数据库数据")
    @GetMapping("/db")
    public CommonResult<Object> dbData() {
        List<DbTableBO> dbTableBOList = this.dbTableService.dbTables();
        return CollUtil.isEmpty(dbTableBOList) ? CommonResult.failed("数据库表无数据") : CommonResult.success(dbTableBOList);
    }

    @ApiOperation("备份数据库脚本或导出数据库表")
    @GetMapping("/backup")
    public ResponseEntity<byte[]> backup(@RequestParam Long id) throws IOException {
        String filePath = this.dbTableService.backup(id);
        // D:\Repository\ifamily\ifamily-server\ifamily-service\ifamily-backend\target\classes\backup\db
        // D:\Repository\ifamily\ifamily-server\ifamily-service\ifamily-backend\target\classes\backup\db\ifamily_acl.sql
        InputStream inputStream = new FileInputStream(filePath);
        // 创建与文件输入流中字节数等大的字节数组
        byte[] byteData = new byte[inputStream.available()];
        // 将文件中的二进制数据读入到缓冲字节数组中
        inputStream.read(byteData);
        inputStream.close();

        // 创建 HttpHeaders 对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        // 设置下载的方式及下载文件名
        headers.add("Content-Disposition", "attachment;filename=" + FileUtil.getName(filePath));
        // 响应 ResponseEntity 对象
        return new ResponseEntity<>(byteData, headers, HttpStatus.OK);
    }

    @ApiOperation("更新管理员禁用状态")
    @PutMapping("/status/{adminId}")
    public CommonResult<Object> updateStatus(@PathVariable Long adminId, @RequestParam Integer newStatus) {
        boolean updateResult = this.adminService.updateStatus(adminId, newStatus);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新管理员账号禁用状态失败");
    }

    @ApiOperation("删除管理员账号")
    @DeleteMapping("/{adminId}")
    public CommonResult<Object> updateStatus(@PathVariable Long adminId) {
        boolean updateResult = this.adminService.removeById(adminId);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求删除管理员账号失败");
    }

}
