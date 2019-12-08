package com.hzg.annotation;

import com.hzg.annotation.model.SetColumn;
import com.hzg.annotation.model.SetTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/** ORM的实现原理
 * @Author: huangzhigao
 * @Date: 2019/12/8 15:02
 */
public class ORMDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> forName = Class.forName("com.hzg.annotation.model.User");      //获取User的实例
        SetTable setTable = forName.getAnnotation(SetTable.class);              //获取类所使用到的注解
        String tableName = setTable.value();
        Field[] fields = forName.getDeclaredFields();                         //获取User的所定义的变量
        StringBuffer sb = new StringBuffer("select ");
        for(int i = 0;i <fields.length;i++){
            SetColumn setColumn = fields[i].getAnnotation(SetColumn.class);     //获取实例变量上所使用的注解
            String columnName = setColumn.columnName();
            if(i==fields.length-1){
                sb.append(columnName+" from ");
            }else{
                sb.append(columnName+",");
            }
        }
        sb.append(tableName);
        System.out.println(sb.toString());
    }
}
