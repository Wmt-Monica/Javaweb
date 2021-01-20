package javawebStage.jDBC.select.data.sheet;

import javawebStage.jDBC.operation.data.sheet.Add_delete_update;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.*;

// 为适应所有表的查询语句来编写的查询语句
public class GradeSelect03 {
    // 1.根据 add_delete_update 类创建连接对象
    Connection conn = new Add_delete_update().conn;


    /**
     *
     * 适用于不同表的通用查询方法

     * @param sql 有关查询的 SQL　语句
     * @throws SQLException　SQL　查询语句存在语法错误所产生的异常
     */
    public <T> void select(Class<T> clazz, String sql) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        // 2.创建PreparedStatement 对象用来执行 SQL 语句
        PreparedStatement pre = conn.prepareStatement(sql);

        // 3.使用 PreparedStatement 对象中的 executeQuery() 方法获取返回的 ResultSet 查询后的结果集
        ResultSet resultSet = pre.executeQuery();

        // 4.根据结果集计算出所有记录的行数及其一条记录的总字段个数
        // 行数：
        resultSet.last();  // 1):将结果集指向最后一条记录的位置
        int rowNum  = resultSet.getRow();  // 2):返回最后一条记录位置的所在行数
        resultSet.beforeFirst();  // 3):将结果集返回到第一条记录的位置
        // 列数：
        ResultSetMetaData metaData = resultSet.getMetaData();  // 1):通过结果集对象使用 getMetaData() 方法来获取元数据 ResultSetMetaData 对象
        int columnNum = metaData.getColumnCount();  // 2):通过元数据对象获取该查询返回结果集中一条记录所含有的字段个数

        // 5.创建由一条记录所对应Java对象所创建的数组
        // 使用传入的类对象 clazz 的 newInstance() 方法来使用该类的无参构造方法实例化创建的记录所对应的数组对象
        Object objects = Array.newInstance(Grade.class, rowNum);

        // 6.处理结果集
        int i = 0;
        while (resultSet.next()) {
            int j = 1;

            // 通过参数对象的 newInstance() 方法引用 T 类型的无参构造方法来实例化一个 T  类型对象
            // 注意：使用如下的方法通过反射创建类对象那么 clazz 这个类对象不能是内部类
            T object = clazz.newInstance();

            while (j <= columnNum) {
                Object columnValue = resultSet.getObject(j);
                String columnLabel = metaData.getColumnLabel(j);
                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(object, columnValue);
                j ++;
            }

            Array.set(objects, i ++, object);
        }

        // 7.释放资源
        resultSet.close();
        pre.close();
        conn.close();

        T[] data = (T[]) objects;
        for (T g : data) {
            System.out.println(g);
        }

    }

    @Test
    public void selectRunning() throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = "select * from grade";
        GradeSelect03 gradeSelect_03 = new GradeSelect03();
        gradeSelect_03.select(Grade.class, sql);
    }

}

class Grade {
    private int grade_id;
    private int stu_id;
    private int class_id;
    private int grade;

    @Override
    public String toString() {
        return "grade{" +
                "grade_id=" + grade_id +
                ", stu_id=" + stu_id +
                ", class_id=" + class_id +
                ", grade=" + grade +
                '}';
    }
}

