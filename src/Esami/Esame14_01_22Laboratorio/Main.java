package Esami.Esame14_01_22Laboratorio;

public class Main {
	private void go() {
		
		int dim = 50;
		
		ResourceManager manager = ResourceManager.getInstance(dim);
		
		for (int i=0; i<dim; ++i) {
			new Worker(manager, i).start();
		}
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
}
