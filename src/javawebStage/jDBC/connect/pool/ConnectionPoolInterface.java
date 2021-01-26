package javawebStage.jDBC.connect.pool;

import java.sql.Connection;

public interface ConnectionPoolInterface {
    void createConnections(int connectionConnection);  // 创建连接对象的抽象方法

    void createConnections(int connectionConnection, String url, String user, String password);  // 创建连接对象的抽象方法

    Connection getConnection(); // 获取连接对象的抽象方法

    void backConnection(Connection conn);  // 归还连接对象的抽象方法
}
