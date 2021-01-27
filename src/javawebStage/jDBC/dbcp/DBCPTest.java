package javawebStage.jDBC.dbcp;

import javawebStage.jDBC.dbcp.util.DBCPUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCPTest {

    @Test
    public void test1() {
        String sql = "insert into animal(animal_id, animal_name) values(44, '燔猴子')";
        Connection conn = new DBCPUtil().getConnection();
        PreparedStatement pre = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.execute();
            System.out.println("SQL 语句执行完毕......");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pre.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
