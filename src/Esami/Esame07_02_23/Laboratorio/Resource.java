package Esami.Esame07_02_23.Laboratorio;

public interface Resource {
	public int getID();
	public void acquire() throws InterruptedException;
	public void release();
	public int use();
}
