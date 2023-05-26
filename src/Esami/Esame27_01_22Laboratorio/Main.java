package Esami.Esame27_01_22Laboratorio;

import java.util.List;

public class Main {
	
	private void go() {
		final int DIM = 10; 
		
		CommanderImpl commander = CommanderImpl.getInstance();
		SlaveManager slave = SlaveManager.getInstance(DIM);
		
		List<Slave> list = slave.getList();
		
		for (int i=0; i<DIM; ++i) {
			System.out.println("Slave " + i);
			commander.command(list.get(i));
		}
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
}
