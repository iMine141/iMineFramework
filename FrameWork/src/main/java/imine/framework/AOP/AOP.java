package imine.framework.AOP;

import java.lang.reflect.Proxy;

/**
 * @author wym
 */
public class AOP {
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(AOP.class.getClassLoader(),
                bean.getClass().getInterfaces(), advice);
    }
}
