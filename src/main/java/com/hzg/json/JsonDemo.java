package com.hzg.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/4 22:55
 */
public class JsonDemo {
    static String jsonStr = "{\"site\":[{\"name\":\"张三\",\"url\":\"www.baidu.com\"},{\"name\":\"张是\",\"url\":\"www.baidu1.com\"}]}";

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONArray site = jsonObject.getJSONArray("site");
        for (Object obj : site) {
            JSONObject jsonObject1 = (JSONObject) obj;
            String name = jsonObject1.getString("name");
            String url = jsonObject1.getString("url");
            System.out.println(name + "---" + url);
        }
    }
}
