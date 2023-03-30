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

        // dobbiamo ottenere tutte le interfacce
		// perchè il proxy dovrà avere tutte le interfacce dell'oggetto target
        Class<?>[] targetInterfaces = targetClass.getInterfaces();
        
        // il class loader è quello che è stato usato per caricare la classe da cui è stato costruito target
        Object proxy = Proxy.newProxyInstance(targetClass.getClassLoader(), targetInterfaces, new InnerInvocationHandler(target));

        @SuppressWarnings("unchecked")
        T result = (T) proxy;
        return result;
    }


    // siccome questa è una classe che ha come unico scopo quello di offrire un metodo statico per la costruzione
	// di questi oggetti, è una classe factory, in particolare non vogliamo che questa classe venga istanziata, e 
	// blocchiamo la possibilità mettendo un unico costruttore vuoto e privato
	private SharedAspect() {
		// blank
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
                    // invocazione del metodo sull'oggetto target
                    Object result = method.invoke(target, arguments);
                    return result;
                }
            } catch (InvocationTargetException exception) { // lanciata da invoke
                throw exception.getCause();
            } catch (Throwable throwable) {
                throw throwable;
            }
        }

    }
}