package javawebStage.jDBC.concurrent;

import javawebStage.jDBC.po.Student;
import javawebStage.jDBC.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class Concurrent01 {

    // 1.使用工具类 JDBCUtil 类的 getConnection() 返回连接数据库的对象
    Connection conn = new JDBCUtil().getConnectionObject();

    @Test
    public void selectTest() {
        String sql = "select stu_id stuID, stu_name stuName from student where stu_name = ?";
        Student[] students = null;
        String stuName = "王梦婷";
        try {
            students = new SelectReturnObjects().select(Student.class, conn, sql, stuName);
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
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Connection 连接对象断开连接失败");
                e.printStackTrace();
            }
        }

        // 打印筛选结果
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
