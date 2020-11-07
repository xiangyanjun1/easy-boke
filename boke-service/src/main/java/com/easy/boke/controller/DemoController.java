package com.easy.boke.controller;

import com.easy.boke.dto.DemoDTO;
import com.easy.boke.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/11/7 0007
 */
@RestController
@RequestMapping("/demoController")
@Api(value = "测试类",tags = "测试类")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @PostMapping("/demoFunction")
    @ApiOperation(value = "测试接口")
    public Integer demo(@RequestBody @Valid DemoDTO demoDTO){
        return demoService.demo(demoDTO);
    }
}
