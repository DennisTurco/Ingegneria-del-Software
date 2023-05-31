package Esami.Esame10e12_01_23.Laboratorio;

public class AbstractFactory {
	
	private static AbstractFactory instance;
	
	private AbstractFactory() {
		// blank
	}
	
	public static AbstractFactory getInstance() {
		if (instance == null) {
			instance = new AbstractFactory();
		}
		
		return instance;
	}
	
	public TemperatureSensor createTemperatureSensor(int ID, int count) {
		return new TemperatureSensorImpl(ID, count);
	}
}
