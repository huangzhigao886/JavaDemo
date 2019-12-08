package com.hzg.annotation.model;

import lombok.Data;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/8 15:03
 */
@SetTable("user_table")
@Data
public class User {
    @SetColumn(columnName = "user_name")
    private String userName;
    @SetColumn(columnName = "user_age")
    private String userAge;
}
