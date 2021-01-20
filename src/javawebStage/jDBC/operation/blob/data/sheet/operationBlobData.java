package javawebStage.jDBC.operation.blob.data.sheet;

import javawebStage.jDBC.operation.data.sheet.Add_delete_update;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 操作 blob 类型数据的增删改查操作
 */
public class operationBlobData {
    // 1.获取连接对象
    Connection conn = new Add_delete_update().conn;

    /**
     * 为存在数据库中 Blob 类型数据有关的添加数据的操作
     * @param sql 有关添加数据的 SQL　语句
     * @param objects　添加字段中所对应的数据数组
     * @throws SQLException　当　SQL　语句语法错误抛出的异常
     * @throws FileNotFoundException　　当数据中存在文件对象是该文件的路径不存在时所抛出的异常
     */
    public void operationBlobDataInsertMethod(String sql, Object[] objects) throws SQLException, FileNotFoundException {
        // 2.实例化预编译并执行 SQL 语句 PreparedStatement 对象
        PreparedStatement pre = conn.prepareStatement(sql);

        // 3.将对应要填充的数据写入到 SQL　语句中 ? 占位符的位置
        for (int i = 1; i <= objects.length; i ++) {
            // 如果添加的数据类型是文件类型就是用输入流的方式将文件中的数据填充到表中
            if (objects[i-1].getClass().equals(File.class)) {
                FileInputStream input = new FileInputStream((File) objects[i-1]);
                pre.setBlob(i, input);
            }else {
                pre.setObject(i, objects[i-1]);
            }
        }

        // 4.执行操作
        pre.execute();
        System.out.println("数据添加成功......");

        // 5.释放资源
        pre.close();
        conn.close();

    }

    /**
     * 有关删除 SQL 语句的操作方法
     * @param sql  有关删除表数据的 SQL　语句
     * @throws SQLException　当传入的删除 SQL 语句存在语法错误
     */
    public void delete(String sql) throws SQLException {
        // 2.实例化预编译并执行的 SQL 语句的 PreparedStatement 对象
        PreparedStatement pre = conn.prepareStatement(sql);

        // 3.执行 SQL 语句
        pre.execute();
        System.out.println("删除数据成功......");

        // 4.释放资源
        pre.close();
        conn.close();
    }

    /**
     * 有关修改表中数据的方法
     * @param sql 有关修改表中数据的 SQL　语句
     * @param updateNewValue　字段被修改之后的值
     * @param updateOlaValue　修改字段中补充的刷选字段值
     * @throws SQLException
     */
    public void update(String sql, Object updateNewValue, Object updateOlaValue) throws SQLException {
        // 2.实例化预编译并用于执行 SQL 语句的 PreparedStatement 对象
        PreparedStatement pre = conn.prepareStatement(sql);


        // 3.填充有关修改表数据的 SQL　语句中占位符所对应的值
        pre.setObject(1, updateNewValue);  // 填充要修改表某一字段修改后的数据
        pre.setObject(2, updateOlaValue);  // 填充修改表数据中补充的筛选条件

        // 4.执行 SQL 语句
        pre.execute();
        System.out.println("修改数据成功......");

        // 5.释放资源
        pre.close();
        conn.close();

    }


    @Test
    public void addRunning() throws FileNotFoundException, SQLException {
        String sql = "insert into people(peo_id, peo_name, peo_photo) values (?, ?, ?)";
        int peo_id = 22;
        String peo_name = "王梦婷";
        File peo_photo = new File("src/javawebStage/jDBC/operation/blob/data/sheet/img/love2.jpg");
        Object[] objects = {peo_id, peo_name, peo_photo};
        new operationBlobData().operationBlobDataInsertMethod(sql, objects);
    }

    @Test
    public void deleteRunning() throws SQLException {
        String sql = "delete from people where peo_name = '王梦婷'";
        new operationBlobData().delete(sql);
    }

    @Test
    public void updateRunning() throws SQLException, FileNotFoundException {
        String sql = "update people set peo_photo = ? where peo_photo = ?";
        FileInputStream updateNewValue = new FileInputStream(new File("src/javawebStage/jDBC/operation/blob/data/sheet/img/love2.jpg"));
        FileInputStream updateOldValue = new FileInputStream(new File("src/javawebStage/jDBC/operation/blob/data/sheet/img/love.jpg"));
        new operationBlobData().update(sql, updateNewValue, updateOldValue);
    }

}
