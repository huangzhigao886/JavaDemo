package com.hzg.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**线程池
 * @Author: huangzhigao
 * @Date: 2019/12/1 23:26
 */
public class ExcutorDemo {
    public static void main(String[] args) {
        ExecutorService excutor = Executors.newCachedThreadPool();
        //创建多个线程
        for(int i = 0;i<10;i++){
            final int index =i;
            excutor.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName()+":"+index);
                }
            });
          if(i == 9){
              excutor.shutdown();
          }
        }

    }
}
