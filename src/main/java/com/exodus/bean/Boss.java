package com.exodus.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认加载在IOC容器中的组件，容器启动会调用无参构造器创建对象，在进行初始化、赋值等操作
 */
@Component
public class Boss {
//    @Autowired
    private Car car;

//    @Autowired
    public Boss(Car car) {
//    public Boss(@Autowired Car car) {
        this.car = car;
        System.out.println("Boss有参构造器...");
    }

    public Car getCar() {
        return car;
    }

//    @Autowired // 可以标注在方法位置，也可以标注在方法位置
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
