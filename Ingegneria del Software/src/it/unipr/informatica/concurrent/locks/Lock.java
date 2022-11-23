package it.unipr.informatica.concurrent.locks;

// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Lock.html

public interface Lock {
	public void lock();
	
	public void unlock();

	public Condition newCondition();

}
