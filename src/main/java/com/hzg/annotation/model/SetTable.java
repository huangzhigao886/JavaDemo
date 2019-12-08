package com.hzg.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**自定义表明注解
 * @Author: huangzhigao
 * @Date: 2019/12/8 15:03
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SetTable {
    String value();
}
