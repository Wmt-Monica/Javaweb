package javawebStage.jDBC.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 工具类：用于返回数据 PreparedStatement 连接对象 conn
 */
public class JDBCUtil {

    private InputStream input = null;

    private Properties prop;

    /**
     * 根据配置文件 Connection.properties 对象创建的连接数据库的 Connection 连接对象
     * @return 返回连接指定数据库的 Connection 连接对象
     */
    public Connection getConnectionObject() {
        // 1.创建读取配置文件的字节流
        input = ClassLoader.getSystemClassLoader().getResourceAsStream(
                "javawebStage/jDBC/connect/to/database/Connect.properties");

        // 2.创建 Properties 类对象存储连接数据库的基本信息键和元素对
        prop = new Properties();
        try {
            // 3.使用 Properties 对象的 load() 方法传入配置文件的字节流对象
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4.获取连接数据库信息
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        String driver = prop.getProperty("driver");

        // 5.加载驱动
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 6.返回 Connection 连接对象 conn
        return conn;
    }


    /**
     * 释放资源的方法
     * @param conn Connection 连接对象
     * @param pre PreparedStatement 预编译并指向 SQL 语句的对象
     * @param resultSet SQL 语句执行后返回的结果集
     */
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
