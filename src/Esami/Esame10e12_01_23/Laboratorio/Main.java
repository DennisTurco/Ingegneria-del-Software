package Esami.Esame10e12_01_23.Laboratorio;

import java.util.ArrayList;
import java.util.List;

public class Main implements TemperatureObserver {
	private static final int N = 10; 	// numero dei Thread nella ThreadPool
	private static final int K = 10;	// numero dei sensori

	@Override
	public void update(TemperatureSensor s) {
		System.out.println("SensorID: " + s.getID() + ", Temperature: " + s.getTemperature());
	}
	
	private void go() {
		AbstractFactory factory = AbstractFactory.getInstance();
		
		List<TemperatureSensor> sensors = new ArrayList<>();
		for (int i=0; i<K; ++i) {
			sensors.add(factory.createTemperatureSensor(i+2, N));
		}
		
		
		// collego gli observer al sensore (1 per sensore)
		for (int i=0; i<K; ++i) {
			sensors.get(i).attach(this);
		}
		
		
		for (int i=0; i<K; ++i) {
			sensors.get(i).start();
		}
		
		
		try {
			Thread.sleep(1000 * 6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		for (int i=0; i<K; ++i) {
			sensors.get(i).stop();
		}
		
		
		// mi funge da join
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("\nTerminated");
		
	}
	
	public static void main(String[] args) {
		int time1 = (int) System.currentTimeMillis();
		new Main().go();
		int time2 = (int) System.currentTimeMillis();
		System.out.println("Time passed: " + (time2 - time1) + " mills");
	}

	
}
