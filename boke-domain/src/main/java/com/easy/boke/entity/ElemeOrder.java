package com.easy.boke.entity;

import com.easy.boke.config.ExcelAnnotation;
import lombok.Data;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/1/4 0004
 */
@Data
public class ElemeOrder {
    @ExcelAnnotation(columnIndex = 0,columnName = "订单号")
    private String orderNo;
}
