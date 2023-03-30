package aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// classe pubblica con un metodo statico pubblico. E' una classe factory perche' non viene istanziata

// un oggetto condiviso puo' essere acceduto da tanti thread e deve garantire che il calcolo della risposta dei suoi messaggi venga realizzata in mutua esclusione; tutte le volte che
// entriamo in un metodo usiamo un lock, per esempio per accedere a una stringa;
public class SharedAspect {

    // taget deve essere un ogetto di tipo interfaccia
    public static <T> T attach(T target) {
        if (target == null) throw new IllegalArgumentException("target == null");

        // ottengo le interfacce
        Class<?> targetClass = target.getClass();
        Class<?>[] targetInterfaces = targetClass.getInterfaces();

        // creo l'oggetto proxy e chimo l'Handler
        Object proxy = Proxy.newProxyInstance(targetClass.getClassLoader(), targetInterfaces, new InnerInvocationHandler(target));

        @SuppressWarnings("unchecked")
        T result = (T) proxy;
        return result;
    }


    // INNER CLASS
    private static class InnerInvocationHandler implements InvocationHandler {
        private Object target;
        private Object lock;
        
        private InnerInvocationHandler(Object target) {
            this.target = target;
            this.lock = new Object();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
            try {
                synchronized (lock) {
                    Object result = method.invoke(target, arguments);
                    return result;
                }
            } catch (InvocationTargetException exception) {
                throw exception.getCause();
            } catch (Throwable throwable) {
                throw throwable;
            }
        }

    }
}