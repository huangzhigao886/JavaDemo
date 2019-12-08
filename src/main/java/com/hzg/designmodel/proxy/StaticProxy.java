package com.hzg.designmodel.proxy;

/**静态代理
 * @Author: huangzhigao
 * @Date: 2019/12/8 23:14
 */
public class StaticProxy implements House {
    private People people;

    public StaticProxy(People people) {
        this.people = people;
    }


    public void buy() {
        System.out.println("我是中介，开始监听");
        people.buy();
        System.out.println("我是中介，结束监听");
    }


    public static void main(String[] args) {
        StaticProxy staticProxy =new StaticProxy(new People());
        staticProxy.buy();
    }
}
