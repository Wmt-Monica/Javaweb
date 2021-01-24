package javawebStage.jDBC.concurrent;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * 实现数据库的筛选操作
 */
public class OperationConcurrent {

    /**
     * 根据 SQL 语句和查询表记录所对应的 Java 对象
     * @param clazz Java 对象 class 类型
     * @param conn 连接数据库的 Connection 连接对象
     * @param sql 有关查询的 SQL 语句字符串对象
     * @param <T> ORM 思想记录所对应的 Java 对象的 class 类型
     * @return 返回 SQL 筛选语句返回的结果集
     */
    public <T> T[] select(Class<T> clazz, Connection conn, String sql,String stuName)
            throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {

        // 1.根据 Connection 连接对象创建 PreparedStatement 预编译和执行对象 pre
        PreparedStatement pre = conn.prepareStatement(sql);

        // 2.填充 SQL 查询语句中占位符的位置上的数据
        pre.setObject(1, stuName);

        // 3.使用 PreparedStatement 对象的 executeQuery() 方法创建 ResultSet 对象存放筛选之后的结果集
        ResultSet resultSet = pre.executeQuery();

        // 4.计算筛选结果集的行数和列数
        // [1]:行数
        resultSet.last();
        int rowCount = resultSet.getRow();
        resultSet.beforeFirst();

        // [2]:列数
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colCount = metaData.getColumnCount();

        // 5.创建类为 clazz 的 array 对象
        T result = (T) Array.newInstance(clazz, rowCount);

        // 6.处理结果集
        for (int i = 0; i < rowCount; i ++) {
            // [1]:根据 clazz 对象的无参构造方法创建 T 类对象
            T t = clazz.newInstance();

            // 如果 ResultSet 结果集从第一条有效数据的前一个开始，如果下一条存在有效的记录，就将 resultSet 的指针下移
            if (resultSet.next()) {
                for (int j = 1; j <= colCount; j ++) {
                    // [2]:使用 resultSet 结果集的指向的当前记录的第 j 个字段的数值
                    Object objectValue = resultSet.getObject(j);

                    // [3]:通过使用 ResultSetMateData 对象的 getColumnLabel() 方法获取索引从 1 开始的属性别名
                    String fieldLabel = metaData.getColumnLabel(j);

                    // [4]:根据所查询表所对应的 Java  对象 clazz 的 getDeclaredField() 方法，传入该类的属性名称来创建 Field 属性对象
                    Field field = clazz.getDeclaredField(fieldLabel);

                    // [5]:为了避免该 Java 对象属性私有化，使用该属性的 setAccessible() 方法传入 true 参数将其属性视为可视化
                    field.setAccessible(true);

                    // [6]:给实例化类对象 t 的 field 该属性赋值为 objectValue
                    field.set(t, objectValue);

                }
            }

            // [7]:给返回值 array 对象 returnResult 的索引从0 开始的第 i-1 个赋值为 t
            Array.set(result, i, t);

        }

        // 7.释放资源
        resultSet.close();
        pre.close();

        // 7.使用 Arrays 类的 asList 静态方法将 Array 数组对象 returnResult 转变成 List 对象
        T[] returnResult = (T[]) result;

        // 8.返回结果 List 对象
       return returnResult;
    }

    /**
     * 修改数据的方法
     * @param conn 用于连接数据库的 Connection 的连接对象
     * @param sql 有关数据修改的 SQL 语句
     * @param updateVale SQL　语句中占位符对应的数据
     * @throws SQLException
     */
    public void update(Connection conn, String sql, Object[] updateVale) throws SQLException {
        PreparedStatement pre = conn.prepareStatement(sql);
        for (int i = 0;i < updateVale.length; i ++) {
            pre.setObject(i+1, updateVale[i]);
        }
        pre.execute();
        System.out.println("修改数据成功......");
        pre.close();
    }
}
