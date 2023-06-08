package Esami.Esame10e12_01_23.Laboratorio;

public interface TemperatureSensor {
	public int getID();
	
	public double getTemperature();
	
	public void start();
	public void stop();
	
	public void attach(TemperatureObserver o); 
	public void detach(TemperatureObserver o); 
}
