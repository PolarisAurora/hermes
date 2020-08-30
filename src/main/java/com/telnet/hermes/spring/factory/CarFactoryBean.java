package com.telnet.hermes.spring.factory;

import com.telnet.hermes.spring.pojo.Car;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Ternura
 * @since 2020/8/30 10:48
 */
public class CarFactoryBean implements FactoryBean<Car> {

    private String carInfo;

    @Override
    public Car getObject() throws Exception {
        String[] carInfoArray = carInfo.split(",");
        Car car = new Car();
        car.setBrand(carInfoArray[0]);
        car.setMaxSpeed(carInfoArray[1]);
        car.setPrice(carInfoArray[2]);
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}
