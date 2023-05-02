package aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import concurrent.ExecutorService;
import concurrent.Executors;
import concurrent.Callback;

public class ActiveAspect {
	private ActiveAspect() {
		// blank
	}
	
	public static <T, A extends Active<T>> ActiveHandler<A> attach(Class<A> activeInterface, T target) {
		return attach(activeInterface, target, 10);
	}
	
	public static <T, A extends Active<T>> ActiveHandler<A> attach(Class<A> activeInterface, T target, int poolSize) {
		if (activeInterface == null) throw new IllegalArgumentException("activeInterface == null");
		if (target == null) throw new IllegalArgumentException("target == null");
		if (poolSize < 1) throw new IllegalArgumentException("poolSize < 1"); 
		
		InnerInvocationHandler InvocationHandler = new InnerInvocationHandler(target, poolSize);
		
		// new Class<?>[] { activeInterface } ????  -->  activeInterface.getClasses() ????
		Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class<?>[] { activeInterface }, InvocationHandler);
		
		@SuppressWarnings("unchecked")
		A result = (A) proxy;
		
		return new InnerActiveHandler<>(result, InvocationHandler);
	}
	
	// INNER CLASS
	private static class InnerActiveHandler<A extends Active<?>> implements ActiveHandler<A> {
		
		private A proxy;
		private InnerInvocationHandler handler;
		
		private InnerActiveHandler(A proxy, InnerInvocationHandler handler) {
			this.proxy = proxy;
			this.handler = handler;
		}
		
		@Override
		public A get() {
			return proxy;
		}

		@Override
		public void shutdown() {
			handler.shutdown();
		}
		
	}
	
	
	// INNNER CLASS
	private static class InnerInvocationHandler implements InvocationHandler {
		
		private Object target;
		private ExecutorService executorService;
		
		private InnerInvocationHandler(Object target, int poolSize) {
			this.target = target;
			this.executorService = Executors.newFixedThreadPool(poolSize);
		}
		
		private void shutdown() {
			executorService.shutdown();
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Class<?>[] parameterTypes = method.getParameterTypes();
			Class<?> targetClass = target.getClass();
			
			int parameterCount = parameterTypes.length;
			
			// controllo che il numero di parametri del metodo sia almeno 1 e che l'ultimo tipo del parametro sia Callback
			
			// va tolto l'ultimo parametro callback perche' non l'abbiamo mai implementato un metodo cosi'
			if (parameterCount > 0 && parameterTypes[parameterCount-1] == Callback.class) {
				
				// rimuovo dalla lista l'ultimo parametro (di tipo Callback)
				parameterCount--;
				Class<?>[] newParameterTypes = (Class<?>[]) Arrays.copyOf(parameterTypes, parameterCount);
				
				// ottengo il riferimento di method ma senza l'ultimo parametro, come quello di prima ma senza l'ultimo parametro (dio tipo Callback)
				// non ne sto creando uno nuovo, ma ne sto cercando uno gia' esistente
				Method passiveMethod = targetClass.getMethod(method.getName(), newParameterTypes);
				
				@SuppressWarnings("unchecked")
				Callback<Object> callback = (Callback<Object>) args[parameterCount];
				
				Object[] newArguments = Arrays.copyOf(args, parameterCount);
				
				// siccome questo metodo prende un Callback sappiamo che e' di tipo void e quindi per convenzione ritorneremo null
				executorService.submit(() -> invokeMethod(passiveMethod, newArguments), callback);
				
				return null;
			}
			
			// se entriamo nell'else significa che siamo in un metodo che ritorna un oggetto di tipo Future<T>
			else {
				Method passiveMethod = targetClass.getMethod(method.getName(), parameterTypes); // se il metodo non viene trovato, viene lanciata un eccezione
				
				// questa submit ritornera' un Future
				return executorService.submit(() -> invokeMethod(passiveMethod, args));
			}
		}
		
		private Object invokeMethod(Method passiveMethod, Object[] args) throws Exception {
			try {
				return passiveMethod.invoke(target, args);
			} catch (InvocationTargetException exception) {
				Throwable cause = exception.getCause();
				if (cause instanceof RuntimeException) throw (RuntimeException) cause;
				if (cause instanceof Exception) throw (Exception) cause;
				else throw exception;
			}
		}
		
	}
}
