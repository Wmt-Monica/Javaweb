package javawebStage.jDBC.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javawebStage.jDBC.c3p0.util.C3p0Util;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 注意：c3p0-config.xml 配置文件需要添加在 src 路径下面，否则创建 Connection 连接对象失败
 */
public class C3p0Test {

    // 使用 C3p0Util 工具类来获取连接对象
    @Test
    public void test2(){
        Connection conn = null;
        PreparedStatement pre = null;

        // 创建 SQL 语句
        String sql = "insert into animal(animal_id, animal_name) values(11, '燔猴子')";

        try {
            // 根据 c3p0 外来连接池创建连接对象
            conn = new C3p0Util().getConnection();

            // 根据连接对象创建执行 SQL 语句的 PreparedStatement 对象
            pre = conn.prepareStatement(sql);

            // 执行 SQL 语句
            pre.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pre.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("SQL 语句执行完毕......");
        }
    }

    @Test
    public void test1(){
        Connection conn = null;
        PreparedStatement pre = null;

        // 以下不传入参数默认时创建配置文件中默认的数据库对象
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        // 创建 SQL 语句
        String sql = "insert into animal(animal_id, animal_name) values(22, '燔猴子')";

        try {
            // 根据 c3p0 外来连接池创建连接对象
            conn = dataSource.getConnection();

            // 根据连接对象创建执行 SQL 语句的 PreparedStatement 对象
            pre = conn.prepareStatement(sql);

            // 执行 SQL 语句
            pre.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pre.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("SQL 语句执行完毕......");
        }
    }
}
