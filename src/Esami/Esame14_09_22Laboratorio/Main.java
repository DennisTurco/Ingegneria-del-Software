package Esami.Esame14_09_22Laboratorio;

public class Main {
	
	private void go() {
		ResourceManagerImpl.getInstance(50, 20);
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
}
