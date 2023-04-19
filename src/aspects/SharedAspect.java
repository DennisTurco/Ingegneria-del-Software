package aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// classe factory

public class SharedAspect {
	
	private SharedAspect() {
		// blank
	}
	
	public static <T> T attach(T target) {
		if (target == null) throw new IllegalArgumentException("target == null");
		
		Class<?> targetClass = target.getClass();
		
		Class<?>[] targetInterfaces = targetClass.getInterfaces();
		
		Object proxy = Proxy.newProxyInstance(targetClass.getClassLoader(), targetInterfaces, new InnerInvocationHandler(target));
		
		@SuppressWarnings("unchecked")
		T result = (T) proxy;
	
		return result;
	}
	
	
	// INNER CLASS
	private static class InnerInvocationHandler implements InvocationHandler {
		
		private Object target;
		private Object mutex;
		
		private InnerInvocationHandler(Object target) {
			this.target = target;
			this.mutex = new Object();
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try {
				synchronized (mutex) {
					Object result = method.invoke(target, args);
					return result;
				}
			} catch (InvocationTargetException exception) {
				throw exception.getCause();
			} catch (Throwable exception) {
				throw exception;
			}
			
		}
		
	}
}
