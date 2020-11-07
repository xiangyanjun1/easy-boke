package com.easy.boke.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/11/7 0007
 */
@Data
@ApiModel(value = "DemoDTO", description = "测试DTO")
public class DemoDTO {

    @NotNull(message = "name不能为空")
    @ApiModelProperty(value = "姓名", example = "向延俊", required = true)
    private String name;
}
