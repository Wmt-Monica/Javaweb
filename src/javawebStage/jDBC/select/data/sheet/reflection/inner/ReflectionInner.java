package javawebStage.jDBC.select.data.sheet.reflection.inner;

import org.junit.Test;
import java.lang.reflect.InvocationTargetException;

public class ReflectionInner {

    public static class inner1{}  // 创建一个被 public static 修饰的内部类

    static class inner2{}  // 创建一个被 public static 修饰的内部类

    class inner3{}  // 创建一个被 public static 修饰的内部类

    // public static 修饰的内部类的反射
    @Test
    public void test1() throws ClassNotFoundException {
        // 当此内部类被 public static 修饰可以直接该类的 class 属性反射回该 Class 对象
        Class<?> c1 = inner1.class;
        System.out.println(c1.getName());

        // 该 inner1 类完整的名字是包名之间采用'.'连接，在外部类中的内部类使用'$'连接
        System.out.println(Class.forName("javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner1"));
        /*
            Java 反射机制中的方法 getConstructor()与getConstructors()
                getConstructor():根据传入的参数的不同可以获取到该映射到的 java 类的相对应的构造方法
                getConstructors():返回的是映射到的 java 类的所有构造方法的数组对象，可以在后面添加 [num],获取到第 num 个构造方法
                                  该构造方法数组对象的属性 length 返回该 java  类对象的所有构造方法的个数
         */
        int constructionMethodCount = Class.forName(
                "javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner1").getConstructors().length;
        System.out.println("类 inner1 含有的构造方法有 "+constructionMethodCount+" 个");
    }

    // static 修饰的内部类的反射
    @Test
    public void test2() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 当此内部类被 public static 修饰可以直接该类的 class 属性反射回该 Class 对象
        Class<?> c1 = inner2.class;
        System.out.println(c1.getName());
        System.out.println(Class.forName("javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner2"));
        int constructionMethodCount = Class.forName(
                "javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner2").getConstructors().length;
        System.out.println("类 inner2 含有的构造方法有 "+constructionMethodCount+" 个");
        // constructionMethodCount = 0
        /*
            输出的类 inner2 的构造方法个数为 0，是因为该类不是公开的所以使用 getConstructions() 方法获取不到该类的构造方法
            我们对于不对外公开的类的构造方法的获取要使用 getDeclaredConstructors() 方法
         */
        int constructionMethodCount2 = Class.forName(
                "javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner2").getDeclaredConstructors().length;
        System.out.println("类 inner2 含有的构造方法有 "+constructionMethodCount2+" 个");

        // 在获取到所有的构造方法数组对象后在后面添加 [num] 获得索引第 num 个构造方法，再通过 newInstance() 获取到该构造方法
        System.out.println(Class.forName(
                "javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner2")
                .getDeclaredConstructors()[0].newInstance());
    }

    // 无修饰的内部类的反射
    @Test
    public void test3() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> c = inner3.class;
        System.out.println(c.getName());
        System.out.println(Class.forName("javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner3"));
        int constructionMethodCount = Class.forName(
                "javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner3").getDeclaredConstructors().length;
        System.out.println("类 inner3 含有的构造方法有 "+constructionMethodCount+" 个");

        // 由于类 inner3 是非静态的内部类,是没有缺省的构造方法，构造时需要传一个外部类的实例作为参数。
        // 所以这里需要在 newInstance() 方法中添加外部类的实例对象作为参数
        System.out.println(Class.forName(
                "javawebStage.jDBC.select.data.sheet.reflection.inner.ReflectionInner$inner3")
                .getDeclaredConstructors()[0].newInstance(new ReflectionInner()));
    }

}
