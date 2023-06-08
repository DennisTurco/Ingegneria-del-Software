package Esami.Esame27_01_22Laboratorio;

import java.util.List;

public class Main {
	
	private static final int S = 10;
	
	public static void main(String[] args) {
		SlaveManager manager = SlaveManager.getInstance(S);
		List<SlaveImpl> slaves = manager.getSlaves();
		
		Commander commander = CommanderImpl.getInstance();
		
		for (SlaveImpl slave: slaves) {
			commander.command(slave);
		}
		
		System.out.println("Terminated");
	}
}
