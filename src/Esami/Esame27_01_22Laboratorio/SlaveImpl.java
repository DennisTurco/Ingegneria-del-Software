package Esami.Esame27_01_22Laboratorio;

public class SlaveImpl implements Slave {
	@Override
	public void executePartA() {
		System.out.println("part A executed");
	}

	@Override
	public void executePartB() {
		System.out.println("part B executed");
	}

	@Override
	public void executePartC() {
		System.out.println("part C executed");
	}

	@Override
	public int finish() {
		return (int) Math.random()*999;
	}

}
