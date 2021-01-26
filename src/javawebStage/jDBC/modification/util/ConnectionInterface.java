package javawebStage.jDBC.modification.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 创建 Connection 接口规范获取连接对象的规范
 */
public interface ConnectionInterface {
    Connection getConnection();  // 获取连接池的抽象方法

    Connection getConnection(String url, String user, String password);  // 根据连接基本信息来获取连接

    void Close(Connection conn, PreparedStatement pre, ResultSet resultSet);  // 在连接数据库操作最后的释放资源操作
}
