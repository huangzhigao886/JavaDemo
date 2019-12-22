package com.hzg.kafka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** 同步的测试
 * @Author: huangzhigao
 * @Date: 2019/12/22 21:46
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService execute = Executors.newCachedThreadPool();
        Future<?> future = execute.submit(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println(i);
            }
        });
        //阻塞，同步
        future.get();
        System.out.println("=======================");
    }
}
