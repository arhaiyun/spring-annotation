package com.exodus.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {

    /**
     * 使用@Value赋值
     * 1.基本数值
     * 2.可以写SpEL: #{}
     * 3.可以${} 读取properties文件中的值（运行环境变量中进行设置）
     *
     */

    @Value("Henry")
    private String name;
    @Value("#{30}")
    private int age;
    @Value("${person.nickname}")
    private String nickName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
