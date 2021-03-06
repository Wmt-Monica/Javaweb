package javawebStage.jDBC.connect.pool;

import javawebStage.jDBC.connect.dao.DAO;
import javawebStage.jDBC.connect.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolTest {

    @Test
    public void test1() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "insert into animal(animal_id, animal_name) values(1, '石燔猩猩')";
        Connection conn = pool.getConnection();
        try {
            new DAO().insert(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            new JDBCUtil().Close(conn, null, null);
        }
    }

    @Test
    public void test2() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "insert into animal(animal_id, animal_name) values(?, ?)";
        Object[] objects = {1, "燔猩猩"};
        Connection conn = pool.getConnection();
        try {
            new DAO().insert(conn, sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            new JDBCUtil().Close(conn, null, null);
        }
    }

    @Test
    public void test3() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "update animal set animal_id = 2 where animal_name = '燔猩猩'";
        Connection conn = pool.getConnection();
        try {
            new DAO().update(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            new JDBCUtil().Close(conn, null, null);
        }
    }

    @Test
    public void test4() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "update animal set animal_id = ? where animal_name = ?";
        Object[] objects = {2, "燔猩猩"};
        Connection conn = pool.getConnection();
        try {
            new DAO().update(conn, sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            new JDBCUtil().Close(conn, null, null);
        }
    }

    @Test
    public void test5() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "delete from animal where animal_name = '石燔猩猩'";
        Connection conn = pool.getConnection();
        try {
            new DAO().delete(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            new JDBCUtil().Close(conn, null, null);
        }
    }

    @Test
    public void test6() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "delete from animal where animal_name = ?";
        Object[] objects = {"燔猩猩"};
        Connection conn = pool.getConnection();
        try {
            new DAO().delete(conn, sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            new JDBCUtil().Close(conn, null, null);
        }
    }

}
