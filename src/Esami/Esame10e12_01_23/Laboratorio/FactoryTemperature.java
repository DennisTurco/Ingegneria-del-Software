package Esami.Esame10e12_01_23.Laboratorio;

public class FactoryTemperature {
	private static FactoryTemperature instance;
	
	private FactoryTemperature() {
		// blank
	}
	
	public static FactoryTemperature getInstance() {
		if (instance == null) {
			instance = new FactoryTemperature();
		}
		return instance;
	}
	
	public TemperatureSensor createTemperatureSensor(int id, int count) {
		return new TemperatureSensorImpl(id, count);
	}
}
