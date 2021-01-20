package javawebStage.jDBC.connect.to.database;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDatabase {

    /**
     * 连接数据库的方式一：
     * @throws SQLException 连接数据库是可能发生的连接异常
     */
    // 运行都是运行 @Test 注解的代码，不能单独运行其他的注解
    @Test
    public void connect1() throws SQLException {

        // 驱动对象: 通过 new 关键字来获取 mysql 的实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        // 寻找数据库对象的 url 对象
        /*
            dbc:mysql:协议
            localhost:ip地址
            3306:默认mysql的端口号
            university:university数据库名称
         */
        String url = "jdbc:mysql://localhost:3306/university";

        // 将用户名和密码存储在 Properties 类对象 info 中
        // 键/值 -> (用户名/root)
        // 键/值 -> (密码/password)
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","mengmengmeng0501");

        // Connection 连接对象 conn:根据驱动对象使用 connect 方法来获取连接对象
        Connection conn = driver.connect(url,info);
        System.out.println(conn);
    }

    /**
     * 在连接数据库方式一的基础上为了更好的适应不用的数据库系统，不再用第三方的 api，
     * 使得程序具有更好的可以执行，通过反射来获取对象(方式一的迭代)
     * @throws Exception 一切可能的异常
     */
    @Test
    public void connect2() {
        /*
        获取 Driver 实现类对象：使用反射
            普及知识点：获取 Class 对象的三种方法
            1. Object -> getClass() 对象的 getClass() 方法获取该对象
            2. 任何数据类型（包括基本数据类型）都有一个 “静态” 的 class 属性 Class clazz = Object.class;
            3. 通过 Class 类的静态方法：Class.forName(String className)【常用】
         */
        try {
            Class clazz = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver) clazz.newInstance();

            String url = "jdbc:mysql://localhost:3306/university";
            Properties info = new Properties();
            info.setProperty("user","root");
            info.setProperty("password","mengmengmeng0501");

            Connection conn = driver.connect(url,info);
            System.out.println(conn);

        } catch (ClassNotFoundException e) {
            System.out.println("未查通过类名 com.mysql.jdbc.Driver 找到该类");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("获取连接实例化 Connection 对象创建失败");
            e.printStackTrace();
        }

    }

    /**
     * 在连接数据库方式二的基础上不对 用户名及其密码进行封装，直接使用 DriverManager 类来注册驱动以及连接数据库
     */
    @Test
    public void connect3() {
        try {
            // 1.获取 Driver 实现类的对象
            Class clazz = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver) clazz.newInstance();

            // 2.提供连接数据库的三个基本信息参数
            String url = "jdbc:mysql://localhost:3306/university";
            String user = "root";
            String password = "mengmengmeng0501";

            // 3.注册驱动:使用 DriverManager 类中的 registerDriver() 方法，传入参数 Driver 驱动对象
            DriverManager.registerDriver(driver);

            // 4.获取连接:使用 DriverManage类中的 getConnection() 方法，传入连接数据库的基本信息参数
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void connect4() throws ClassNotFoundException, SQLException {
        // 1.提供数据库连接的三个基本信息
        String url = "jdbc:mysql://localhost:3306/university";
        String user = "root";
        String password = "mengmengmeng0501";

        // 2.加载驱动
        /*
            此处相对于连接方式三中做出的改进是省略了驱动的注册
            原因：在 Driver 类加载的同时该类中的静态代码块中已经完成了驱动的注册：如下
            static {
                try {
                    java.sql.DriverManager.registerDriver(new Driver());
                } catch (SQLException E) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
         */
        Class.forName("com.mysql.jdbc.Driver");

        // 3.获取链接
        Connection conn = DriverManager.getConnection(url,user,password);

        System.out.println(conn);
    }

    /**
     * 在方式四的基础上通过创建配置文件来与数据库建立起连接
     * 实现了数据与代码的分离，实现了解耦
     * @throws Exception
     */
    @Test
    public void connect5() throws Exception {
        // getClassLoader 返回类的加载器
        // getResourceAsStream 返回用于读取指定资源的输入流 -> 传入参数为指定的路径
        // load 传入字节流里面包含键和元素对数据的简单面向行流

        // 1.通过获取该类 class 对象的类加载器对象来获取指定路径下的配置文件的字节流
        // 补充：通过ClassLoader.getSystemClassLoader()也可以获取到该类的加载器
//        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(
//        "JavawebStage/JDBC/ConnectToDatabase/Connect.properties");
        InputStream input = ConnectDatabase.class.getClassLoader().getResourceAsStream(
                "javawebStage/jDBC/connect/to/database/Connect.properties");

        // 2.创建 Properties 对象来存入获取输入字节流中的键和元素对的值
        Properties pros = new Properties();
        pros.load(input);

        // 3.通过键值来获取配置文件下所有连接数据库的基本信息
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driver = pros.getProperty("driver");

        // 4.加载驱动
        Class.forName(driver);

        // 5.创建连接对象
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);

    }

}
