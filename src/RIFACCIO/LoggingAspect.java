package RIFACCIO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class LoggingAspect {

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss,SSS");
    
    private LoggingAspect() {
        //blank
    }

    public static <T> T attach(T target) {
        if (target == null) throw new NullPointerException("target == null");

        Class<?> targetClass = target.getClass();
        Class<?>[] targetInterfaces = targetClass.getInterfaces();

        Object proxy = Proxy.newProxyInstance(targetClass.getClassLoader(), targetInterfaces, new InnerInvocationHandler(target));
        
        @SuppressWarnings("unchecked") 
        T result = (T) proxy;
        return result;
    }

    private static void log(String message) {
        String now = DATE_FORMAT.format(System.currentTimeMillis());
        System.out.printf("[%s %s] %s\n", Thread.currentThread().getName(), now, message);
    }


    // INNER CLASS
    private static class InnerInvocationHandler implements InvocationHandler {

        private Object target;

        private InnerInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            
            String name = method.getName();
            
            try {
                log("In " + name + " " + Arrays.toString(args));
                Object result = method.invoke(target, args);
                log("Out " + name + " " + result);
                return result;
            } catch (InvocationTargetException exception) {
                Throwable cause = exception.getCause();
                log("Out " + name + " " + cause.getMessage());
                throw cause;
            } catch (Throwable throwable) {
                throw throwable;
            }
        }

    }
}
