package javawebStage.jDBC.po;

public class Account {
    String peopleName;
    int money;

    @Override
    public String toString() {
        return "Account{" +
                "peopleName='" + peopleName + '\'' +
                ", money=" + money +
                '}';
    }
}
