package javawebStage.jDBC.select.data;

import javawebStage.jDBC.po.Teacher;
import javawebStage.jDBC.operation.data.sheet.Add_delete_update;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class TeacherSelect02 {
    // 1.根据 add_delete_update 类获取 Connection 连接对象 conn
    Connection conn = new Add_delete_update().conn;

    /**
     * 根据查询语句来获取单条数据
     * @param sql 有关查询的　SQL 语句
     * @throws SQLException SQL 语句错误产生的异常
     */

    public void select(String sql) throws SQLException, NoSuchFieldException, IllegalAccessException {
        // 2.创建 PreparedStatement 对象并执行 SQL 查询
        PreparedStatement pre = conn.prepareStatement(sql);

        // 3.使用 PreparedStatement 对象的 executeQuery() 方法返回查询生成的 ResultSet 对象结果集
        ResultSet resultSet = pre.executeQuery();

        // 4.获取结果集对象的筛选出的记录的行数
        /*
            由于 ResultSet 类没有直接获取结果集的方法，所以我们可以采用如下的方式获取记录行数
            1.根据 ResultSet 类对象的 last 属性将结果集对象指向最后一个结果记录
            2.根据 ResultSet 类对象的 getRow() 方法，获取当前 resultSet 对象做指向的最后一行记录所在的行数返回的 int 型数据
            3.最后为了之后迭代遍历结果集，使用ResultSet 类 beforeFirst 属性中第一条记录的属性重新赋值给 resultSet 结果集对象
         */
        resultSet.last(); //移到最后一行
        int rowCount = resultSet.getRow(); //得到当前行号，也就是记录数
        resultSet.beforeFirst(); //如果还要用结果集，就把指针再移到初始化的位置

        // 5.获取一条记录的字段个数
        /*
            由于 resultSet 类中没有直接获取结果集中记录的字段数，所以我们可以使用如下的方式获取字段数
            1.通过结果集 resultSet 对象的 getMataData() 方法创建 ResultSetMetaData 对象
            2.通过调用创建好的元数据的 getColumnCount() 方法来获取结果集的总列数
         */
        ResultSetMetaData metaData = resultSet.getMetaData();  // 获取结果集的元数据
        int columnCount = metaData.getColumnCount();  // 获取元数据的列数

        // 创建 student 类的数据用于存放查询出的 student 对象的记录
        Teacher[] teachers = new Teacher[rowCount];

        // 6.处理结果集
        /*
            resultSet 默认在第一条数据的前一个
            next():判断结构集是否有下一条数据，如果存在就返回 true,并将 resultSet 对象下移一位，否则就返回 false
         */
        int i = 0;
        while (resultSet.next()) {
            int j = 1;

            // 通过无参构造方法创建 student 对象
            Teacher tea = new Teacher();

            // 1):获取有效一条 student 数据的所有字段的值
            while (j <= columnCount) {
                // 获取查询到的结果集对象中的记录的第 j 个字段值
                Object columnValue = resultSet.getObject(j);
                // 1.获取查询到的数据的字段名称，通过调用元素据的 getColumnLabel() 方法第 j 个字段的别名，如果未取别名，默认是字段名（推荐）
                // 注意：此处不推荐使用 ResultSetMetaData 对象的 getColumnName() 方法是为了确保MySQL与JDBC的兼容性
                String columnLabel = metaData.getColumnLabel(j);
                // 2.通过反射来获取 student 类中的属性对象来准确的对不同的字段进行赋值
                Field field = Teacher.class.getDeclaredField(columnLabel);
                // 3.当该属性处于被保护的情况下，调用该属性的setAccessible() 方法，传入参数 true,这样也可以直接对该属性进行一些操作
                field.setAccessible(true);
                // 4.设置之前无参构造方法创建好的 student 对象对该其 field 属性赋值
                field.set(tea, columnValue);
                // 将该记录的字段索引下移加一
                j++;
            }

            // 将创建好的 student 对象存入 students 数组中去
            teachers[i++] = tea;
        }

        // 7.释放资源
        resultSet.close();  // 结果集的资源关闭
        pre.close();  // 执行 SQL　语句　PreparedStatement 对象资源关闭
        conn.close();  // Connection 连接对象资源关闭

        // 使用 foreach 遍历 student 数组
        for (Teacher tea : teachers) {
            System.out.println(tea);
        }
    }

    @Test
    public void selectRunning() throws NoSuchFieldException, IllegalAccessException, SQLException {
        String sql = "select teacher_id teacherID, teacher_name teacherName from teacher";
        new TeacherSelect02().select(sql);
    }

}
