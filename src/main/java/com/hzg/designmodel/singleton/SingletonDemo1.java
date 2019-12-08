package com.hzg.designmodel.singleton;

/** 懒汉式
 * @Author: huangzhigao
 * @Date: 2019/12/8 15:44
 */
public class SingletonDemo1 {

    private static SingletonDemo1 singletonDemo1;

    //构造函数私有化，不能被反射
    private SingletonDemo1(){

    }

    //获取实例对象
    public static SingletonDemo1 getInstance(){
        if(singletonDemo1 == null){
            //懒汉式线程不安全，在多线程时，可能同时进入这个方法创建实例，导致线程不安全
            synchronized(SingletonDemo1.class){
                singletonDemo1 = new SingletonDemo1();
            }
        }
        return singletonDemo1;
    }


    public static void main(String[] args) {
        SingletonDemo1 singletonDemo1 = SingletonDemo1.getInstance();
        SingletonDemo1 singletonDemo2 = SingletonDemo1.getInstance();
        System.out.println(singletonDemo1 == singletonDemo2);
    }
}
