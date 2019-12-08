package com.hzg.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**自定义字段注解
 * @Author: huangzhigao
 * @Date: 2019/12/8 15:04
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SetColumn {
    String columnName();
}
