package javawebStage.jDBC.operation.blob.data.sheet.po;

import java.sql.Date;

/**
 * 创建查询表结果的 student 对象
 * ORM编程思想（object relation mapping）
 * 一个数据表对应一个 java 类
 * 表中的一条记录对应 java 类中的一个对象
 * 表中的一个字段对应 java 类的一个属性
 */
public class Student {
    private int stuID;
    private String stuName;
    private String stuSex;
    private String stuTelephone;
    private Date stuBirthday;

    @Override
    public String toString() {
        return "Student{" +
                "stuID=" + stuID +
                ", stuName='" + stuName + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuTelephone='" + stuTelephone + '\'' +
                ", stuBirthday=" + stuBirthday +
                '}';
    }
}
