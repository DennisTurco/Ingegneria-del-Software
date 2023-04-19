package aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Arrays;

// un LoggingAspect serve per tracciare in modo permanente il comportamento del nostro sistema e identificare
// anomalie; prendere un oggetto e appiccica qualcosa che garantisca che tutti i messaggi/risposte siano tradotti;
public class LoggingAspect {

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss,SSS");

    // non vogliamo che questa classe venga istanziata
    private LoggingAspect() {
        // blank
    }

    public static <T> T attach(T target) {
        if (target == null) throw new IllegalArgumentException("target == null");

        // ottengo le interfacce prendendo le classi del target
        Class<?> targetClass = target.getClass();

        // prendo le interfacce del target
        Class<?>[] targetInterfaces = targetClass.getInterfaces();

        // costruiamo un proxy che utilizza lo stesso class loader del target, utilizza le stesse interfacce del target
		// e poi ci mette un giotte invocation handler
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
	
	    /* 
	    'InnerInvocationHandler(Object target)': è il costruttore della classe 'InnerInvocationHandler' che prende in input un oggetto 'target' 
	    di tipo 'Object' e lo assegna alla variabile di istanza 'target'.
	    */
        private InnerInvocationHandler(Object target) {
            this.target = target;
        }

	    /*
	    Il metodo `invoke` viene chiamato ogni volta che un metodo viene chiamato su un oggetto proxy creato tramite la classe `Proxy`. 
	    Il metodo `invoke` prende tre parametri:
		- `proxy`: l'oggetto proxy su cui è stato chiamato il metodo;
		- `method`: l'oggetto `Method` che rappresenta il metodo chiamato sull'oggetto proxy;
		- `arguments`: l'array di oggetti che rappresentano gli argomenti passati al metodo chiamato sull'oggetto proxy.
	    */
        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
            
            String name = method.getName();

		/* 
		Se la chiamata del metodo ha avuto successo, viene salvato il risultato del metodo nella variabile result. 
		Viene poi chiamato il metodo log per stampare un messaggio che indica che il metodo è terminato e qual è stato il risultato. 
		Infine, il metodo restituisce il risultato.
		*/
            try {
                log("In " + name + " " + Arrays.toString(arguments));
                Object result = method.invoke(target, arguments);
                log("Out " + name + " " + result);
                return result;
            } catch (InvocationTargetException exception) { //eccezione che proviene da invoke
                Throwable cause = exception.getCause();
                log("Out " + name + " " + cause.getMessage());
                throw cause;
            } catch (Throwable throwable) {
                log("Out " + name + " " + throwable.getMessage());
                throw throwable;
            }

        }
    }
}
