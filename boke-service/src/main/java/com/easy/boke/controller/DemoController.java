package com.easy.boke.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easy.boke.dto.DemoDTO;
import com.easy.boke.entity.ElemeOrder;
import com.easy.boke.mq.DelayProducer;
import com.easy.boke.service.DemoService;
import com.easy.boke.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
//    @Autowired
//    private RedissonClient redissonClient;
    @Autowired
    private DelayProducer delayProducer;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/demoFunction")
    @ApiOperation(value = "测试接口")
    public Integer demo( @Valid DemoDTO demoDTO){
//        redisTemplate.opsForHash().put("refund", "yo",true);
//        redisTemplate.expire("refund",2,TimeUnit.MINUTES);
//        Boolean o = (Boolean) redisTemplate.opsForHash().get("refund", "yo");
//        System.out.println(o);
        return demoService.demo(demoDTO);
    }

    @GetMapping("/release")
    @ApiOperation(value = "release")
    public void release(){
        demoService.rlease();
    }

    @GetMapping("/send")
    @ApiOperation(value = "send")
    public void send(){
        redisTemplate.opsForValue().set("shop",100);
        Object shop = redisTemplate.opsForValue().get("shop");
        System.out.println(shop);
//        redisTemplate.opsForValue().set("shop",shop-1);
//        delayProducer.sendCustomMsg("yoyo");
    }


    @GetMapping("/lock")
    @ApiOperation(value = "lock")
    public Boolean lock() throws InterruptedException {

//        RLock lock = redissonClient.getLock("lockKey");
//        boolean b = lock.tryLock(5L,60L, TimeUnit.SECONDS);
//        System.out.println(b);
//        if (b){
//            try {
//                Thread.sleep(20000);
//                return true;
//            }finally {
//                lock.unlock();
//            }
//        }else {
            return false;
//        }
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
