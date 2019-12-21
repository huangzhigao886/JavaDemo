package com.hzg.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/** 使用httpClient
 * @Author: huangzhigao
 * @Date: 2019/12/10 21:28
 */
public class HttpDemo {

    public static void get(String url) throws IOException {
        //创建一个默认连接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //发送一个get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            //获取结果
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
        }
    }

    public static void main(String[] args) throws IOException {
        HttpDemo.get("http://www.itmayiedu.com");
    }
}
