package Esami.Esame10e12_01_23.Laboratorio;

import java.util.ArrayList;

public class Main implements TemperatureObserver {
	
	private static final int N = 10;
	private static final int K = 10;
	
	private void go() {
		FactoryTemperature factory = FactoryTemperature.getInstance();
		
		ArrayList<TemperatureSensor> temperatures = new ArrayList<>();
		for (int i=0; i<K; ++i) {
			temperatures.add(factory.createTemperatureSensor(i+2, N));
			temperatures.get(i).attach(this);
			temperatures.get(i).start();
		}
		
		
		// sleep
		try {
			Thread.sleep(1000 * 6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// interrupts
		for (TemperatureSensor temperature: temperatures) {
			temperature.stop();
		}
		
		System.out.println("Terminated");
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
	

	@Override
	public void update(TemperatureSensor o) {
		System.out.println("Observer observes temperature: " + o.getTemperature());
	}
}
