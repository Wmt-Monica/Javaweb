package javawebStage.jDBC.po;

/**
 * 创建查询表结果的 student 对象
 * ORM编程思想（object relation mapping）
 * 一个数据表对应一个 java 类
 * 表中的一条记录对应 java 类中的一个对象
 * 表中的一个字段对应 java 类的一个属性
 */
public class Classes {
    private int classID;
    private int teacherID;
    private String className;

    @Override
    public String toString() {
        return "Classes{" +
                "classID=" + classID +
                ", teacherID=" + teacherID +
                ", className='" + className + '\'' +
                '}';
    }
}
