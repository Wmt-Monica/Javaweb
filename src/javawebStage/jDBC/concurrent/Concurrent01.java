package javawebStage.jDBC.concurrent;

import javawebStage.jDBC.po.Account;
import javawebStage.jDBC.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class Concurrent01 {

    // 1.使用工具类 JDBCUtil 类的 getConnection() 返回连接数据库的对象
    Connection conn = new JDBCUtil().getConnectionObject();

    @Test
    public void selectTest() {
        String sql = "select people_name peopleName, money from account where people_name = ?";
        Account[] peoples = null;
        String peopleName = "王梦婷";
        try {
            peoples = new OperationConcurrent().select(Account.class, conn, sql, peopleName);
        } catch (SQLException e) {
            System.out.println("SQL 查询语句错误");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("方法参数中 Java 类参数对象没有未查询到该属性");
            e.printStackTrace();
        }finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                System.out.println("Connection 连接对象断开连接失败");
//                e.printStackTrace();
//            }
        }

        // 打印筛选结果
        for (Account a : peoples) {
            System.out.println(a);
        }
    }


    /**
     * 注意，这里的查看数据使用的 selectTest() 方法时，休要在 finally 中的代码块进行注释
     * 原因：连接中断之后，设置的不能自动提交会失效，连接中断会自动提交数据
     */
    @Test
    public void updateTest() {
        String sql = "update account set money = ? where people_name = ?";
        Object[] updateValues = {13000, "王梦婷"};

        try {
            // 查看当前数据库的连接的隔离级别
            System.out.println("数据库默认的隔离级别为 "+conn.getTransactionIsolation());

            // 设置连接数据库的隔离级别（脏读）->（读未提交）
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            // 取消自动提交
            conn.setAutoCommit(false);

            // 执行修改数据的方法
            System.out.println("修改数据之前数据结果：");
            selectTest();  // 在修改数据之前查看数据
            new OperationConcurrent().update(conn, sql, updateValues);

            // 数据修改之后查看数据
            System.out.println("数据修改之后数据结果：");
            selectTest();

            // 执行数据修改后程序休眠 10 秒
            Thread.sleep(10000);

            // 数据修改之后，但程序未停止之前在查看数据
            System.out.println("数据修改之后，但程序未停止之前数据结果：");
            selectTest();
        } catch (SQLException | InterruptedException e) {
            System.out.println("SQL 语法错误");
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
