package com.hzg.designmodel.proxy;

import org.omg.CORBA.portable.InvokeHandler;

import javax.sound.midi.Soundbank;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/** jdk动态代理
 * @Author: huangzhigao
 * @Date: 2019/12/8 23:26
 */
public class JdkProxy implements InvocationHandler {

    private People people;

    public JdkProxy(People people){
        this.people = people;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是中介，开始代理");
        Object invoke = method.invoke(people, args);
        System.out.println("我是中介，结束代理");
        return invoke;
    }


    public static void main(String[] args) {
        People people = new People();
        JdkProxy jdkProxy = new JdkProxy(people);
        //获取动态代理实例
        House house = (House) Proxy.newProxyInstance(people.getClass().getClassLoader(), people.getClass().getInterfaces(), jdkProxy);
        house.buy();
    }
}
