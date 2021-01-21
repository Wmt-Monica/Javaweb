package javawebStage.jDBC.operation.blob.data.sheet;

import javawebStage.jDBC.operation.blob.data.sheet.po.Grade;
import javawebStage.jDBC.operation.blob.data.sheet.po.People;
import javawebStage.jDBC.operation.data.sheet.Add_delete_update;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.*;

public class SelectBlobDataSheet {

    // 1.创建连接对象
    Connection conn = new Add_delete_update().conn;
    public <T> void select(Class<T> clazz, String sql) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        // 2.创建预编译执行 SQL 语句的 PreparedStatement 对象
        PreparedStatement pre = conn.prepareStatement(sql);

        // 3.使用 PreparedStatement 对象的 executeQuery() 方法返回查询语句返回的记录结果集
        ResultSet resultSet = pre.executeQuery();

        // 4.获取所得到的结果集的总行数
        resultSet.last();
        int rowCount = resultSet.getRow();
        resultSet.beforeFirst();

        // 5.获取结果集中一条记录的字段数量（列数）
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 6.创建 Object 类型的对象数组用于存储所得到结果集的每一条记录所对应的 java 对象
        // 使用 Array 类中的 newInstance() 方法实例化一个 Array 数组对象
        // 参数一：对象的 class； 参数二：所创建 Array 数组对象的大小
        Object objects = Array.newInstance(clazz, rowCount);

        // 7.处理结果集
        // [1]:循环 objects 结果集 Java 对象数组的索引
        int BlobCount = 3;
        for (int i = 0; i < rowCount; i ++) {

            // [2]:创建 T 类型的对象并使用 clazz 的 newInstance() 方法使用其无参的构造方法实例化
            T t = clazz.newInstance();

            // [3]:resultSet 只想结果集第一条有效记录的前一个，next() 方法是判断是否存在下一条记录，并将 resultSet 下移
            if (resultSet.next()) {

                // [4]:循环结果集中的每一条记录
                for (int j = 1; j <= columnCount; j ++) {
                    // 1):使用 ResultSet 对象中的 getObject(j) 获取当前记录中索引从 1 开始的所有数据
                    Object columnValues = resultSet.getObject(j);

                    // 2):使用 ResultSetMataData 元数据对象中的 getColumnLabel() 方法来获取查询到的一条记录中 第 j 个字段的别名
                    String columnLabel = metaData.getColumnLabel(j);

                    // 3):通过查询语句中记录所对应的 java  类 clazz 中的 getDeclaredField() 反射获取类属性方法传入 columnLabel(属性名称)来创建 Field 属性对象
                    Field field = clazz.getDeclaredField(columnLabel);

                    // 4):为了避免属性的私有化，我们使用 Field 属性对象的 setAccessible() 方法将其属性设置为可操作化
                    field.setAccessible(true);

                    System.out.println(field.getType());

                    // 通过 Field 属性对象的 getType() 方法可以获得所获得该属性的类型，判断是否为 Blob 类型
                    // 是 Blob 类型：将其获取到的 BLob 数据以字节流的形式写入到固定的文件中中去
                    if (field.getType().equals(Blob.class)) {
                        InputStream in = (resultSet.getBlob(j)).getBinaryStream();
                        System.out.println("BlobCount = "+BlobCount);
                        FileOutputStream output = new FileOutputStream("src/javawebStage/jDBC/operation/blob/data/sheet/img/love"+BlobCount+".jpg");
                        byte[] rush = new byte[1024];
                        int length;
                        while ((length = in.read(rush)) != -1) {
                            output.write(rush, 0 , length);
                        }
                        BlobCount ++;
                    }else {
                        // 5):最后通过使用 Field 属性对象的 set() 方法对其该属性对应的 Java 对象进行赋值
                        // 参数一：属性赋值的 java  对象；参数二：赋值属性的 value 值
                        field.set(t, columnValues);
                    }

                }
            }

            // [5]:将实例化并重新对其所有查询的属性重新赋值的 Java 对象填充到查询对象数组中去
            Array.set(objects, i, t);
        }

        // 8.释放资源
        resultSet.close();
        pre.close();
        conn.close();

        // 9.打印输出所有结果集
        T[] data = (T[]) objects;
        for (T t : data) {
            System.out.println(t);
        }

    }

    @Test
    public void selectRunning() throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        String sql = "select peo_id peopleID, peo_name peopleName, peo_photo peoplePhoto from people";
        new SelectBlobDataSheet().select(new People().getClass(), sql);
    }


}


