package com.hzg.reflect;

import com.hzg.model.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.List;

/**利用反射实现SpringIOC的操作
 * @Author: huangzhigao
 * @Date: 2019/12/6 23:04
 */
public class SpringIOCDemo {
    private String xmlPath;

    public SpringIOCDemo(String xmlPath){
        this.xmlPath = xmlPath;
    }

    /**
     * SpringIOC是实现原理是：1解析xml，2获取类路径，3通过反射获取实例
     * @param beanId
     * @return
     * @throws DocumentException
     */
    public Object getBean(String beanId) throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(this.getClass().getClassLoader().getResourceAsStream(xmlPath));
        //获取xml的跟节点
        Element rootElement = read.getRootElement();
        //获取根节点下的所有子节点
        List<Element> sonElement = rootElement.elements();

        Object obj = null;
        for(Element element:sonElement){
            //获取beanId
            String beanID = element.attributeValue("id");
            if(!beanID.equals(beanId)){
                continue;
            }
            String classPath = element.attributeValue("class");
            //获取实例名
            Class<?> className = Class.forName(classPath);
            obj = className.newInstance();
            //该bean下的子节点
            List<Element> elements = element.elements();
            for(Element element1:elements){
                String name = element1.attributeValue("name");
                String value = element1.attributeValue("value");
                Field declaredField = className.getDeclaredField(name);
                declaredField.setAccessible(true);
                declaredField.set(obj,value);
            }
        }
        return obj;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, InstantiationException, DocumentException, IllegalAccessException {
        SpringIOCDemo springIOCDemo = new SpringIOCDemo("user.xml");
        User user1 = (User) springIOCDemo.getBean("user1");
        System.out.println(user1.getName());
        System.out.println(user1.getSex());
    }
}
