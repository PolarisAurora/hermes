package com.telnet.hermes.spring;

import com.telnet.hermes.spring.pojo.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ternura
 * @since 2020/8/29 17:24
 */
public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-test.xml");
        Object testBean = applicationContext.getBean("testBean");
        System.out.println(testBean);

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
    }
}
