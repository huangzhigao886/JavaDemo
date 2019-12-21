package com.hzg.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/9 22:35
 */
public class CacheManager {
    private Map<String,Cache> cacheMap = new HashMap<>();

    public void put(String key,Object value){
        put(key,value,null);
    }

    public void put(String key,Object value,Long timeout){
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setValue(value);
        if(timeout != null){
            cache.setTimeout(timeout);
        }
        cacheMap.put(key,cache);
    }


    public void del(String key){
        cacheMap.remove(key);
    }


    public Cache get(String key){
        Cache cache = cacheMap.get(key);
        if(cache == null){
            return null;
        }
        return cacheMap.get(key);
    }


    public static void main(String[] args) {
        CacheManager cacheManager = new CacheManager();
        cacheManager.put("user","123");
        System.out.println("保存成功");
        Cache user = cacheManager.get("user");
        System.out.println(user);
    }


}
