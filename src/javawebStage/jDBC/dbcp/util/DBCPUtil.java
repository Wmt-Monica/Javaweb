package javawebStage.jDBC.dbcp.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBCP 连接池的辅助工具类来获取连接池对象和连接对象
 * 注意：外部引用的连接池框架其配置文件的名字的要求和各个参数的名称要符合要求保持一致
 */
public class DBCPUtil {
    public static DataSource dataSource;

    static {
        // 记载找 DBCP.properties 配置文件输入流
        InputStream input = DBCPUtil.class.getClassLoader().getResourceAsStream("src/javawebStage/jDBC/dbcp/util/DBCP.properties");

        try {
            // 加载输入流
            Properties pro = new Properties();
            pro.load(input);

            // 创建数据源
            dataSource = BasicDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 创建静态方法返回连接池对象
    public static DataSource getDataSource() {
        return dataSource;
    }

    // 创建静态方法用户返回连接池中的 Connection 连接对象
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
