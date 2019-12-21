package com.hzg.cache;

import lombok.Data;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/9 22:32
 */
@Data
public class Cache {

    private String key;
    private Object value;
    //超时时间
    private long timeout;
}
