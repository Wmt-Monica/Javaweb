package javawebStage.jDBC.modification.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

/**
 * 创建 JDBCUtil 工具类实现 Connection 接口来实现获取连接对象的方法
 */
public class JDBCUtil implements ConnectionInterface {

    private InputStream input = null;  // 读取配置文件中连接数据库中的信息

    private Properties pro = null;

    private LinkedList<Connection> connectionList;

    public JDBCUtil(LinkedList<Connection> pool) {
        this.connectionList = pool;
    }

    public JDBCUtil() {

    }

    /**
     * 根据配置文件来获取配置文件中的默认连接数据库
     * @return 返回连接对象 Connection
     */
    @Override
    public Connection getConnection() {
        // 读取 Connect.properties 配置文件对象的内容
        input = ClassLoader.getSystemClassLoader().getResourceAsStream(
                "javawebStage/jDBC/connect/database/Connect.properties");

        // 实例化 Properties 对象存储连接数据库中各个信息之间的信息键/元素对
        pro = new Properties();
        try {
            pro.load(input);  // 将输入流读取到的信息使用 Properties 的 load() 方法存入
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过 Properties 对象获取信息
        String url = pro.getProperty("url");
        String user = pro.getProperty("user");
        String password = pro.getProperty("password");

        // 使用 DriverManager 对象加载驱动
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 创建 Connection 连接类的修饰类
        Connection mConn = new ModificationConnection(conn, connectionList);
        return mConn;
    }

    /**
     * 传入连接数据库的参数，来创建并返回指定数据库的连接对象
     * @param url 连接数据库的 URL
     * @param user 数据库用户名
     * @param password 数据库连接对应用户密码
     * @return 返回指定的数据库连接对象 Connection
     */
    @Override
    public Connection getConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection mConn = new ModificationConnection(conn, connectionList);
        return mConn;
    }

    /**
     * 连接数据库操作完毕之后的释放资源操作
     * @param conn Connection 连接对象
     * @param pre 执行数据库 SQL 语句的 PreparedStatement 对象
     * @param resultSet 执行 SQL 语句的 ResultSet 返回值集合
     */
    @Override
    public void Close(Connection conn, PreparedStatement pre, ResultSet resultSet) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
