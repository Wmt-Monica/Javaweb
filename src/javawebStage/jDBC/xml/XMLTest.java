package javawebStage.jDBC.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class XMLTest {

    public void method() {
        System.out.println("反射该对象成功");
    }

    @Test
    public void test2() {
        try {
            // 1.获取解析器
            SAXReader saxReader = new SAXReader();

            // 2.创建 Document 文旦对象
            Document doc = saxReader.read("src/javawebStage/jDBC/xml/util/web-app.xml");

            // 3.获取根元素对象
            Element rootElement = doc.getRootElement();

            // 4.获取根元素中的第三个 servlet 元素中的 servlet-class 子元素的 text 文本内容
            List<Element> childElements = rootElement.elements("servlet");
            String clazzName = childElements.get(2).element("servlet-class").getText();
            System.out.println(clazzName);

            // 5.通过获取到的类名称来反射创建类对象
            XMLTest xmlTest = (XMLTest) Class.forName(clazzName).newInstance();

            // 6.使用通过反射创建的 XMLTest 对象
            xmlTest.method();


        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {

        try {
            // 1. 获取解析器
            SAXReader saxReader = new SAXReader();

            // 2.获取 document 文档对象
            Document doc = saxReader.read("src/javawebStage/jDBC/xml/util/web-app.xml");

            // 3.获取根元素
            Element rootElement = doc.getRootElement();
//            System.out.println(rootElement.getName());  // 测试是否成功获取到 Document 根元素对象，测试打印根元素对象的标签名字
//            System.out.println(rootElement.attributeValue("version"));  // 使用根元素对象来获取其根元素对象的属性值

            // 4.使用根元素对象的 elements() 方法获取根元素下的子元素 List 集合
            List<Element> childElements = rootElement.elements();

            // 5.遍历子元素对象并打印输出所有子元素的标签名称
            for (Element e : childElements) {
                System.out.println("---"+e.getName());
                if ("servlet".equals(e.getName())) {
                    List<Element> e1 = e.elements();
                    for (Element e2 : e1) {
                        System.out.println("------"+e2.getName());
                    }
                }

                if ("servlet-mapping".equals(e.getName())) {  // 当根元素的子元素的标签名为 servlet-mapping 时
                    // 使用子标签的 element() 方法，传入指定标签名称，打印输出 servlet-mapping 的指定子标签的的子标签名称及其在标签中的 text 文本内容
                    System.out.print("------"+e.element("url-pattern").getName());
                    System.out.println("text(文本内容) : "+e.element("url-pattern").getText());
                    System.out.print("------"+e.element("servlet-name").getName());
                    System.out.println("text(文本内容) : "+e.element("servlet-name").getText());
                }
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
