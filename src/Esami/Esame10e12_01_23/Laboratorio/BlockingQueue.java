package Esami.Esame10e12_01_23.Laboratorio;

public interface BlockingQueue<T> {
	public T pop() throws InterruptedException ;
	public void push(T elem);
}
