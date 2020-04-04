package com.exodus.bean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color> {
    /**
     * 返回一个color对象，这个对象会添加到容器中
     * */
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     *
     * @return true:bean为单实例， false:多实例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
