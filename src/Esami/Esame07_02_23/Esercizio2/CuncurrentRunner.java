package Esami.Esame07_02_23.Esercizio2;


public class CuncurrentRunner {
	
	private static Object mutex;
	private static Object result;
	
	public static Object execute(Task t1, Task t2) throws InterruptedException {
		mutex = new Object();
		
		CallbackThread first = new CallbackThread(t1, CuncurrentRunner::setResult);
		CallbackThread second = new CallbackThread(t2, CuncurrentRunner::setResult);
		
		// EQUIVALE A 
		/*
		CallbackThread second2 = new CallbackThread(t2, new Callback() {
			@Override
			public void onSuccess(Object elem) {
				synchronized (mutex) {
					CuncurrentRunner.result = result;
					mutex.notifyAll();
				}
				
			}
		});
		*/
		
		first.start();
		second.start();
		
		synchronized (mutex) {
			while(result == null) mutex.wait();
			return result;
		}
	}
	
	public static void setResult(Object result) {
		synchronized (mutex) {
			CuncurrentRunner.result = result;
			mutex.notifyAll();
		}
	}
	
	// INNER CLASS
	private static class CallbackThread extends Thread {
		private CallbackThread(Task task, Callback callback) {
			super(() -> {
				Object result = task.run();
				callback.onSuccess(result);
			});
		}
	}
}
