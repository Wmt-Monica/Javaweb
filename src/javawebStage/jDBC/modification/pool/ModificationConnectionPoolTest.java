package javawebStage.jDBC.modification.pool;

import javawebStage.jDBC.modification.dao.DAO;
import javawebStage.jDBC.modification.util.JDBCUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 修饰 Connection 类对象在连接池中的使用
 */
public class ModificationConnectionPoolTest {

    @Test
    public void test1() {
        ConnectionPool pool = new ConnectionPool();
        Connection conn = pool.getConnection();
        String sql = "insert into animal(animal_id, animal_name) values(3, '燔猴子')";
        try {
            new DAO().insert(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            System.out.println("SQL 语句执行完毕......");
            new JDBCUtil().Close(conn, null, null);
        }
    }
}
