package javawebStage.jDBC.connect.pool;

import javawebStage.jDBC.connect.util.JDBCUtil;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 连接池
 */
public class ConnectionPool implements ConnectionPoolInterface{

    private static LinkedList<Connection> connectionList = new LinkedList<>();  // 创建存放连接对象的连接池 LinkedList 集合对象

    public LinkedList<Connection> getConnectionList() {  // 创建获取连接池对象的 get() 方法
        return connectionList;
    }

    // 静态初始化连接池
    static {
        for (int i = 0; i < 5; i ++) {
            Connection conn = new JDBCUtil().getConnection();  // 根据改包下的 JDBCUtil 工具类创建连接对象
            connectionList.add(conn);  // 将创建的连接对象存放进 connectionList 连接池 LinkedList 集合中
        }
    }

    @Override
    public void createConnections(int connectionCount) {
        for (int i = 0; i < connectionCount; i ++) {
            Connection conn = new JDBCUtil().getConnection();  // 根据改包下的 JDBCUtil 工具类创建连接对象
            connectionList.add(conn);  // 将创建的连接对象存放进 connectionList 连接池 LinkedList 集合中
        }
    }

    @Override
    public void createConnections(int connectionConnection, String url, String user, String password) {
        for (int i = 0; i < connectionConnection; i ++) {
            Connection conn  = new JDBCUtil().getConnection(url, user, password);  // 根据改包下的 JDBCUtil 工具类创建连接对象
            connectionList.add(conn);  // 将创建的连接对象存放进 connectionList 连接池 LinkedList 集合中
        }
    }

    @Override
    public Connection getConnection() {
        if (connectionList.size() > 0) {  // 当连接池中的剩余连接对象个数大于零时
            return connectionList.remove();
        }else {  // // 当连接池中没有剩余连接对象时，使用createConnections() 方法创建一个新的连接对象添加入连接池中
            createConnections(1);
            return connectionList.remove();
        }
    }

    @Override
    public void backConnection(Connection conn) {  // 将传入的连接对象添加回连接池中
        connectionList.add(conn);
    }
}
