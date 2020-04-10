package com.exodus.test;

import org.junit.Test;

public class ExceptionTest {

    public int testMethod() {
        int i = 0;
        try {
            i++;
            System.out.println("try i = " + i);
            i = i / 0;
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch i = " + i);
            return i;
        } finally {
            i++;
            System.out.println("finally i = " + i);
            return i;
        }
//        return i;
    }
    @Test
    public void test01() {
        System.out.println("invoke test method, result: " + testMethod());
    }
}
