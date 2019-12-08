package com.hzg.designmodel.singleton;

/**饿汉式，在类被加载时，初始化实例
 * @Author: huangzhigao
 * @Date: 2019/12/8 15:50
 */
public class SingletonDemo2 {
    //类被加载时调用
    private static final SingletonDemo2 singleDemo2 = new SingletonDemo2();

    private SingletonDemo2(){

    }

    public static SingletonDemo2 getInstance(){
        return singleDemo2;
    }

    public static void main(String[] args) {
        SingletonDemo2 instance1 = SingletonDemo2.getInstance();
        SingletonDemo2 instance2 = SingletonDemo2.getInstance();
        System.out.println(instance1 == instance2);
    }
}
