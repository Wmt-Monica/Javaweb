package javawebStage.jDBC.connect.pool;

import javawebStage.jDBC.connect.dao.DAO;
import org.junit.Test;
import java.sql.SQLException;

public class PoolTest {

    @Test
    public void test1() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "insert into animal(animal_id, animal_name) values(1, '石燔猩猩')";
        try {
            new DAO().insert(pool.getConnection(), sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "insert into animal(animal_id, animal_name) values(?, ?)";
        Object[] objects = {1, "燔猩猩"};
        try {
            new DAO().insert(pool.getConnection(), sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "update animal set animal_id = 2 where animal_name = '燔猩猩'";
        try {
            new DAO().update(pool.getConnection(), sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "update animal set animal_id = ? where animal_name = ?";
        Object[] objects = {2, "燔猩猩"};
        try {
            new DAO().update(pool.getConnection(), sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "delete from animal where animal_name = '石燔猩猩'";
        try {
            new DAO().delete(pool.getConnection(), sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6() {
        ConnectionPool pool = new ConnectionPool();
        String sql = "delete from animal where animal_name = ?";
        Object[] objects = {"燔猩猩"};
        try {
            new DAO().delete(pool.getConnection(), sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
