package javawebStage.jDBC.po;

import java.sql.Blob;

/**
 * 创建查询表结果的 student 对象
 * ORM编程思想（object relation mapping）
 * 一个数据表对应一个 java 类
 * 表中的一条记录对应 java 类中的一个对象
 * 表中的一个字段对应 java 类的一个属性
 */
public class People {
    private int peopleID;
    private String peopleName;
    private Blob peoplePhoto;

    @Override
    public String toString() {
        return "People{" +
                "peopleID=" + peopleID +
                ", peopleName='" + peopleName + '\'' +
                ", peoplePhoto=" + peoplePhoto +
                '}';
    }
}
