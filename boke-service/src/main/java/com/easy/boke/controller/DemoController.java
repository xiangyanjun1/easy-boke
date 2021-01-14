package com.easy.boke.controller;

import com.easy.boke.dto.DemoDTO;
import com.easy.boke.entity.ElemeOrder;
import com.easy.boke.service.DemoService;
import com.easy.boke.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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

    @PostMapping("/checkingOrderExcel")
    @ApiOperation(value = "对账")
    public void importExcel(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile excel = multipartRequest.getFile("filename");
        try {
            List<ElemeOrder> excelData = ExcelUtil.readExcelObject(excel, ElemeOrder.class);
            //检查每列数据
            for (int i = 0; i < excelData.size(); i++) {
                System.out.println(excelData.get(i).getOrderNo());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
