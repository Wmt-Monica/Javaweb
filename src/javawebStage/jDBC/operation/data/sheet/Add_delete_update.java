package javawebStage.jDBC.operation.data.sheet;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Add_delete_update {

    InputStream input = null;  // 存储配置文件的输入字节流
    Properties prop = null;  // 存储连接数据库各个基本信息的键加元素对 Properties 对象
    String url = null; // 连接数据库的 URL 对象
    String user = null;  // 数据库用户名称
    String password = null;  // 登录用户密码
    String driver = null;  // 数据库驱动对象
    public Connection conn = null;  // 连接数据库对象
    PreparedStatement pre = null;  // 在 PreparedStatement 对象中执行 SQL 语句

    // 将连接数据库的操作在该类的构造方法中实现
    public Add_delete_update() {
        try {
            // 1.创建配置文件字节流
            input = ClassLoader.getSystemClassLoader().getResourceAsStream(
                    "javawebStage/jDBC/connect/database/Connect.properties");

            // 2.创建 Properties 类对象存储俩捏数据库的信息键和元素对，使用 load() 方法传入配置文件的字节流对象
            prop = new Properties();
            prop.load(input);

            // 3.获取连接数据库信息
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            driver = prop.getProperty("driver");

            // 4.加载驱动
            Class.forName(driver);

            // 5.创建 Connection 连接对象
            conn = DriverManager.getConnection(url, user, password);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 为数据库 university 下的 student 表添加数据
     * @param stu_id 学生 ID
     * @param stu_name 学生姓名
     * @param stu_sex 学生性别
     * @param stu_telephone 学生电话
     * @param stu_birthday 学生出生日期
     * @throws SQLException sql 语句错误所抛出的异常
     */
    public void add(int stu_id, String stu_name, String stu_sex, String stu_telephone, java.sql.Date stu_birthday) throws SQLException {

        // 6.创建预编译的 sql 语句
        // 注意：这里采用 ? 占位符来充当参数，后面会对从 1 索引的占位符依次对数据进行填充
        String sql = "insert into student(stu_id, stu_name, stu_sex, stu_telephone,stu_birthday) values (?,?,?,?,?)";

        // 7.创建 PreparedStatement 实例
        // 根据连接对象 conn 使用 prepareStatement() 方法，传入SQL语句对象　sql
        pre = conn.prepareStatement(sql);

        // 8.使用set数据类型()方法来对索引从1开始的占位符数据进行填充
        pre.setInt(1,stu_id);
        pre.setString(2,stu_name);
        pre.setString(3,stu_sex);
        pre.setString(4,stu_telephone);
        pre.setDate(5,stu_birthday);

        // 9.执行操作
        pre.execute();
        System.out.println("数据添加成功......");

        // 10.资源的释放
        close();

    }

    /**
     * 根据 SQL　字符串语句来对数据库对象进行删除操作
     * @param sql 有关删除操作 SQL 语句
     * @throws SQLException 当 SQL 语句错误所抛出的异常
     */
    public void delete(String sql) throws SQLException {
        // 1.创建 PrepareStatement 实例
        // 使用连接对象 conn 的 prepareStatement() 方法，传入 SQL 操作语句
        pre = conn.prepareStatement(sql);

        // 2.执行操作
        pre.execute();
        System.out.println("数据删除成功......");

        // 3.释放资源
        close();
    }

    /**
     * 根据 SQL　语句进行数据的修改的方法
     * @param sql　有关数据修改的　SQL 语句
     * @throws SQLException 当 SQL　语句错误所抛出的异常
     */
    public void update(String sql) throws SQLException {
        // 1.创建 PrepareStatement 实例
        // 使用连接对象 conn 的 prepareStatement() 方法，传入 SQL 语句
        pre = conn.prepareStatement(sql);

        // 2.执行操作
        pre.execute();
        System.out.println("数据修改成功.....");

        // 3.释放资源
        close();
    }

    /**
     * 资源释放方法
     */
    public void close() throws SQLException {
        pre.close();  // prepareStatement 对象的关闭
        conn.close();  // Connection 连接对象的关闭
    }

    // 调用添加数据的方法
    @Test
    public void addRunning() throws ParseException, SQLException {
        int stu_id = 22;
        String stu_name = "石燔憨憨";
        String stu_sex = "男";
        String stu_telephone = "18307988122";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2020-9-17");
        java.sql.Date stu_birthday = new java.sql.Date(date.getTime());
        new Add_delete_update().add(stu_id, stu_name, stu_sex, stu_telephone, stu_birthday);
    }

    // 调用删除指定数据的方法
    @Test
    public void deleteRunning() throws SQLException {
        // 创建 SQL　语句字符串对象
        String sql = "delete from student where stu_name = '杨同志'";
        new Add_delete_update().delete(sql);
    }

    // 调用修改数据的方法
    @Test
    public void updateRunning() throws SQLException {
        // 创建 SQL 语句字符串对象
        String sql = "update student set stu_name = '杨同志' where stu_name = '杨日健'";
        new Add_delete_update().update(sql);
    }

}
