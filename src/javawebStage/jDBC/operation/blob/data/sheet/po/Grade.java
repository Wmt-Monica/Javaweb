package javawebStage.jDBC.operation.blob.data.sheet.po;

/**
 * 创建查询表结果的 student 对象
 * ORM编程思想（object relation mapping）
 * 一个数据表对应一个 java 类
 * 表中的一条记录对应 java 类中的一个对象
 * 表中的一个字段对应 java 类的一个属性
 */
public class Grade {
    private int gradeID;
    private int stuID;
    private int classID;
    private int grade;

    @Override
    public String toString() {
        return "Grade{" +
                "gradeID=" + gradeID +
                ", stuID=" + stuID +
                ", classID=" + classID +
                ", grade=" + grade +
                '}';
    }
}
