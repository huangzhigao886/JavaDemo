package com.hzg.model;

import lombok.Data;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/4 23:28
 */
@Data
public class User {

    private String name;
    private String sex;

    public User(){
        System.out.println("无参构造函数");
    }

}
