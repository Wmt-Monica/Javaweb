package javawebStage.jDBC.modification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 数据库增删改查的方法集合 DAO 类
 */
public class DAO implements DAOInterface {

    PreparedStatement pre = null;

    @Override
    public <T> void insert(Connection conn, String sql) throws SQLException {
        // 通过使用参数 Connection 连接对象来实例化 PreparedStatement 对象
        pre = conn.prepareStatement(sql);

        // 执行参数 SQL 语句
        pre.execute();

        System.out.println("数据添加成功......");
    }

    @Override
    public <T> void insert(Connection conn, String sql, Object[] objects) throws SQLException {
        // 通过使用参数 Connection 连接对象来实例化 PreparedStatement 对象
        pre = conn.prepareStatement(sql);

        // 填充占位符的位置
        for (int i = 1; i <= objects.length; i ++) {
            pre.setObject(i, objects[i-1]);
        }

        // 执行参数 SQL 语句
        pre.execute();

        System.out.println("数据添加成功......");
    }

    @Override
    public <T> void delete(Connection conn, String sql) throws SQLException {
        // 通过使用参数 Connection 连接对象来实例化 PreparedStatement 对象
        pre = conn.prepareStatement(sql);

        // 执行参数 SQL 语句
        pre.execute();

        System.out.println("数据删除成功......");
    }

    @Override
    public <T> void delete(Connection conn, String sql, Object[] objects) throws SQLException {
        // 通过使用参数 Connection 连接对象来实例化 PreparedStatement 对象
        pre = conn.prepareStatement(sql);

        // 填充占位符的位置
        for (int i = 1; i <= objects.length; i ++) {
            pre.setObject(i, objects[i-1]);
        }

        // 执行参数 SQL 语句
        pre.execute();

        System.out.println("数据删除成功......");
    }

    @Override
    public <T> void update(Connection conn, String sql) throws SQLException {
        // 通过使用参数 Connection 连接对象来实例化 PreparedStatement 对象
        pre = conn.prepareStatement(sql);

        // 执行参数 SQL 语句
        pre.execute();

        System.out.println("数据修改成功......");
    }

    @Override
    public <T> void update(Connection conn, String sql, Object[] objects) throws SQLException {
        // 通过使用参数 Connection 连接对象来实例化 PreparedStatement 对象
        pre = conn.prepareStatement(sql);

        // 填充占位符的位置
        for (int i = 1; i <= objects.length; i ++) {
            pre.setObject(i, objects[i-1]);
        }

        // 执行参数 SQL 语句
        pre.execute();

        System.out.println("数据修改成功......");
    }
}
