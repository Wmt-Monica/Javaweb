package javawebStage.jDBC.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JDBCTest {
    public static void main(String[] args) {
        System.out.println("main");
    }

    // 运行都是运行 @Test 注解的代码，不能单独运行其他的注解
    @Test
    public void Test() {
        System.out.println("Test is running!");
    }

    @Before
    public void TestBefore() {
        System.out.println("Test is before running");
    }

    @After
    public void TestAfter() {
        System.out.println("Test is after running");
    }
}
