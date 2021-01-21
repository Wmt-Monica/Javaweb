package javawebStage.jDBC.operation.batch;

import javawebStage.jDBC.operation.data.sheet.Add_delete_update;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 批量操作：
 *      1）:首先将 mysql-connector_jaa-5.17-bin.jar 换成而更高点的版本，应该该版本不支持批量操作
 *      2）：在配置文件中的 url 之后添加参数 (?rewriteBatchedStatements=true)
 *      3）：设置不允许自动提交数据 conn.setAutoCommit(false)
 *      4）：在攒积到一定的 SQL 语句之后在执行 SQL 语句
 *      5）：最后手动提交数据 conn.commit();
 */
public class BatchOperation {
    // 1.创建连接对象
    Connection conn = new Add_delete_update().conn;

    public void batchAddOperation(String  sql) throws SQLException {
        // 2.创建预编译执行的 PreparedStatement 对象
        PreparedStatement pre = conn.prepareStatement(sql);

        // 3.设置不允许自动提交数据
        conn.setAutoCommit(false);

        // 4.批量添加数据
        for (int i = 1; i <= 200000; i ++) {
            // 填充占位符
            pre.setObject(1, i);
            pre.setObject(2, "name_"+i);

            // 5.积攒添加操作的 SQL 语句
            pre.addBatch();

            // 6.设置当积攒到一定数目 SQL 语句久指执行
            if (i % 500 == 0) {
                // 7.执行批量的添加数据操作的 SQL 语句,执行批量的 SQL 语句 使用 executeBatch() 方法
                pre.executeBatch();

                // 8.清空 PreparedStatement 对象中积攒的 SQL 语句
                pre.clearBatch();
            }

        }

        // 9.提交数据
        conn.commit();
        System.out.println("批量添加数据完成......");
    }

    @Test
    public void addRunning() throws SQLException {
        String sql = "insert into animal(animal_id, animal_name) values (?, ?)";
        long startTime = System.currentTimeMillis();
        new BatchOperation().batchAddOperation(sql);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时长："+(endTime-startTime));
    }
}
