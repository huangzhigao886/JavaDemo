package com.hzg.thread;

/**
 * 同步代码块，保证线程安全
 *
 * @Author: huangzhigao
 * @Date: 2019/11/26 21:37
 */
class TicketThread implements Runnable {
    private int count = 100;
    private Object oj = new Object();

    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            synchronized (oj) {
                if (count > 0) {
                    System.out.println(Thread.currentThread().getName() + "卖出第" + (100 - count + 1) + "张票");
                    count--;
                }
            }

        }
    }
}

public class SyncDemo {
    public static void main(String[] args) {
        TicketThread ticketThread = new TicketThread();
        Thread t1 = new Thread(ticketThread);
        Thread t2 = new Thread(ticketThread);
        t1.start();
        t2.start();
    }
}
