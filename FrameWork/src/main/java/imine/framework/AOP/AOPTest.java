package imine.framework.AOP;

/**
 * @author wym
 */
public class AOPTest {
    public static void main(String[] args) {
        // 1.创建一个MethodInvocation实现类
        MethodInvocation logTask = () -> System.out.println("log task start");
        HelloServiceImpl helloService = new HelloServiceImpl();

        // 2.创建一个Advice
        Advice beforeAdvice = new Advice(helloService, logTask);

        // 3.为目标对象生成代理
        HelloService helloServiceProxy = (HelloService) AOP.getProxy(helloService, beforeAdvice);
        helloServiceProxy.sayHelloWorld();
    }
}
