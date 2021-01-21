package javawebStage.jDBC.transaction;

import javawebStage.jDBC.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 关于银行用户转账之间的事务处理
 */
public class TransactionAccounts {

    /**
     * 执行事务中的语句的方法
     * @param conn
     * @param sql
     * @throws SQLException
     */
    public void update(Connection conn, String sql, Object[] objects) throws SQLException {
        // 1.创建预编译 SQL 语句的 PreparedStatement 对象
        PreparedStatement pre = conn.prepareStatement(sql);

        // 2.填充 SQL 语句中的占位符
        for (int i = 0; i < objects.length; i ++) {
            pre.setObject(i+1, objects[i]);
        }
        // 2.执行 SQL 语句
        pre.execute();

        // 3.连接对象不释放，将连接 java 和 数据库的 PreparedStatement 对象释放
        pre.close();
    }

    @Test
    public void accounts() {
        String sql1 = "update account set money = money - ? where people_name = ?";
        String sql2 = "update account set money = money + ? where people_name = ?";
        int money = 100;
        String peopleName1 = "石燔";
        String peopleName2 = "王梦婷";
        Object[] objects1 = {money,peopleName1};
        Object[] objects2 = {money,peopleName2};
        Connection conn = new JDBCUtil().getConnectionObject();

        try {
            // 1.设置不能主动提交数据
            conn.setAutoCommit(false);

            new TransactionAccounts().update(conn, sql1, objects1);
            int i = 100/0;
            new TransactionAccounts().update(conn, sql2, objects2);

            // 2.事务所有的 SQL 语句成功执行操作，最后将数据进行提交
            conn.commit();
            System.out.println("事务处理成功......");
        } catch (Exception e) {
            System.out.println("事务执行出现异常，停止继续执行，将事务进行回滚操作");
            try {
                // 满足对事务的处理方式，一旦一组 SQL　语句中有一条语句因为异常而中断了执行，就将其所有之前执行的语句全部进行回滚操作
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            // 3.事务处理最后进行资源的释放
            new JDBCUtil().Close(conn, null, null);
        }
    }
}
