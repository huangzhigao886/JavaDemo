package com.hzg.thread;

/**
 * 生产者与消费者
 *
 * @Author: huangzhigao
 * @Date: 2019/11/26 22:36
 */

/**
 * 数据
 */
class CommonValue {
    public String name;
    public String sex;
    public boolean flag = false;
}


/**
 * 生产者
 */
class Producer extends Thread {
    CommonValue commonValue;

    public Producer(CommonValue commonValue) {
        this.commonValue = commonValue;
    }

    public void run() {
        int count = 0;
        while (true) {
            synchronized (commonValue) {
                if(commonValue.flag){
                    try {
                        commonValue.wait();
                    } catch (InterruptedException e) {

                    }
                }
                if (count == 0) {
                    commonValue.name = "小明";
                    commonValue.sex = "男";
                } else {
                    commonValue.name = "小红";
                    commonValue.sex = "女";
                }
                commonValue.flag = true;
                count = (count + 1) % 2;
                commonValue.notify();
            }
        }

    }
}


/**
 * 消费者
 */
class Consumer extends Thread {
    CommonValue commonValue;

    public Consumer(CommonValue commonValue) {
        this.commonValue = commonValue;
    }

    public void run() {
        while (true) {
            synchronized (commonValue) {
                if(!commonValue.flag){
                    try {
                        commonValue.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(commonValue.name + "--" + commonValue.sex);
                commonValue.flag = false;
                commonValue.notify();
            }
        }
    }
}

public class ProduceAndConsumer {
    public static void main(String[] args) {
        CommonValue commonValue = new CommonValue();
        Producer producer = new Producer(commonValue);
        Consumer consumer = new Consumer(commonValue);
        producer.start();
        consumer.start();
    }
}
