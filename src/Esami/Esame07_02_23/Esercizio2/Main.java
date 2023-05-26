package Esami.Esame07_02_23.Esercizio2;

import java.util.Random;

public class Main {
	
	private static Random random;
	
	private void go() {
		random = new Random();
		int value;
		
		try {
			value = (Integer) CuncurrentRunner.execute(Main::exampleTask, Main::exampleTask);
			System.out.println("Valore ritornato: " + value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Object exampleTask() {
		int value = random.nextInt(5000) + 2000;
		try {
			System.out.println("Starting to sleep for: " + value + "ms");
			Thread.sleep(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
}
