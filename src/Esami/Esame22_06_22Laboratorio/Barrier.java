package Esami.Esame22_06_22Laboratorio;

public interface Barrier {
	public void add(Object object);
	public void remove(Object object);
	public void await() throws InterruptedException;
}
