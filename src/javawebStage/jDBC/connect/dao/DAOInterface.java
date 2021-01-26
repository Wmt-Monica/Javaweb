package javawebStage.jDBC.connect.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAOInterface {

    <T> void insert(Connection conn, String sql) throws SQLException;  // 增加表中数据

    <T> void insert(Connection conn, String sql, Object[] objects) throws SQLException;  // 增加表中数据

    <T> void delete(Connection conn, String sql) throws SQLException;  // 删除表中数据

    <T> void delete(Connection conn, String sql, Object[] objects) throws SQLException;  // 删除表中数据

    <T> void update(Connection conn, String sql) throws SQLException;  // 修改表中数据

    <T> void update(Connection conn, String sql, Object[] objects) throws SQLException;  // 修改表中数据
}
