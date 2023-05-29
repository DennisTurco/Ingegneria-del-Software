package Esami.Esame07_02_23.Laboratorio;

public interface Lock {
	public void lock() throws InterruptedException;
	public void unlock();
}
