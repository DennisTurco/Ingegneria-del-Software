package it.unipr.informatica.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SharedAspect {
	
	// partiamo da un oggetto T e costruiamo un oggetto dello stesso tipo dell'argomento
	public static <T> T attach(T target) {
		if (target == null) throw new IllegalArgumentException("target == null");
		
		// vogliamo sapere tutte le interfacce che implementa
		Class<?> targetClass = target.getClass();
		
		// dobbiamo ottenere tutte le interfacce
		// perchè il proxy dovrà avere tutte le interfacce dell'oggetto target
		Class<?>[] targetInterfaces = targetClass.getInterfaces();
			
		// il class loader è quello che è stato usato per caricare la classe da cui è stato costruito target
		Object proxy = Proxy.newProxyInstance(targetClass.getClassLoader(), targetInterfaces,
				new InnerInvocationHandler(target));

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
	// classe statica e quindi senza stato
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
				// sezione critica
				synchronized (lock) {
					// invocazione del metodo sull'oggetto target
					Object result = method.invoke(target, arguments);

					return result;
				}
			} catch (InvocationTargetException exception) { // lanciata da invoke
				throw exception.getCause();
			} catch (Throwable throwable) { // altra eccezione
				throw throwable;
			}
		}
	}
}
