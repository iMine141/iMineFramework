package imine.framework.IoC;

/**
 * @author wym
 */
public class IoCTest {
    public static void main(String[] args) throws Exception {

        XmlBeanFactory applicationContext = new XmlBeanFactory("bean.xml");
        User user1 = (User) applicationContext.getBean("user1");
        System.out.println(user1);
    }
}
