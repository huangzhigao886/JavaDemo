package com.hzg.reflect;

import com.hzg.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** 反射机制
 * @Author: huangzhigao
 * @Date: 2019/12/4 23:27
 */
public class ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?> forName = Class.forName("com.hzg.model.User");
        //获取实例，相当于new User()无参
        Object obj = forName.newInstance();
        //获取申明的方法
        Method[] declaredMethods = forName.getDeclaredMethods();
        for(Method method:declaredMethods){
            //获取方法的名字和返回值
            System.out.println(method.getName()+":"+method.getReturnType());
        }
        //获取变量
        Field[] declaredFields = forName.getDeclaredFields();
        for(Field field:declaredFields){
            System.out.println(field.getName());
        }

        //修改私有变量属性值
        Field name = forName.getDeclaredField("name");
        //是否允许修改私有变量属性值
        name.setAccessible(true);
        name.set(obj,"zhangsan");
        User user = (User)obj;
        System.out.println(user.getName());
    }
}
