package com.easy.boke.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/1/4 0004
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation {
    /**
     * 列索引
     *
     * @return
     */
    public int columnIndex() default 0;

    /**
     * 列名
     *
     * @return
     */
    public String columnName() default "";
}
