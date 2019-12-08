package com.hzg.annotation;

/** 自定义注解
 * @Author: huangzhigao
 * @Date: 2019/12/8 14:25
 */


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Target标识自定义的注解使用在方法还是类上等等   ElementType.METHOD:表示自定义的注解使用在方法上
 * @Retention 表示允许通过反射获取信息
 * 自定义了一个DefinedAnnotation注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefinedAnnotation {
    //表示使用该注解时，需要使用以下的两个参数,default 表示给定默认值
    String value() default "zhang";
    int beanId() default 1;
}


class DemoTest{
    @DefinedAnnotation()
    public void add(){};
}
