package javawebStage.jDBC.c3p0.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3p0Util {
    // 创建 ComboPooledDataSource 对象传入参数，指定名字为 ShiFan 的连接
    public static ComboPooledDataSource dataSource = new ComboPooledDataSource("ShiFan");

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new  RuntimeException(e);
        }
    }
}
