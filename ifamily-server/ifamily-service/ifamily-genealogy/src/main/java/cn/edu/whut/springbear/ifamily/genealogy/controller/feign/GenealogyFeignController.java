package cn.edu.whut.springbear.ifamily.genealogy.controller.feign;

import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 11:08
 */
@Api(tags = "家族调用接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/genealogy/feign")
public class GenealogyFeignController {

    private final UserGenealogyService userGenealogyService;

    @ApiOperation("查询用户默认家族 ID")
    @GetMapping
    public Long getUserDefaultGenealogyId(@ApiParam("用户 ID") @RequestParam Long userId) {
        return this.userGenealogyService.getDefault(userId).getGenealogyId();
    }

}
