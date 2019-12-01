package com.hzg.thread;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/1 23:05
 */
class Res {
    private ThreadLocal<Integer> count = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int getNum() {
        int count = this.count.get() + 1;
        this.count.set(count);
        return count;
    }
}


class ThreadLocalDe extends Thread {
    Res res = new Res();

    public ThreadLocalDe(Res res) {
        res = this.res;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(currentThread().getName() + ":" + res.getNum());
        }
    }


}

public class ThreadLocalDemo {

    public static void main(String[] args) {
        Res res = new Res();
        ThreadLocalDe threadLocalDe1 = new ThreadLocalDe(res);
        ThreadLocalDe threadLocalDe2 = new ThreadLocalDe(res);
        ThreadLocalDe threadLocalDe3 = new ThreadLocalDe(res);
        threadLocalDe1.start();
        threadLocalDe2.start();
        threadLocalDe3.start();

    }
}
