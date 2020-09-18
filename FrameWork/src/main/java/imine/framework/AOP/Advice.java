package imine.framework.AOP;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wym
 */
public class Advice implements InvocationHandler {
    private Object bean;
    private MethodInvocation methodInvocation;
    public Advice(Object bean, MethodInvocation methodInvocation) {

        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在目标方法执行前调用通知
        methodInvocation.invoke();
        return method.invoke(bean, args);
    }
}
