package Esami.Esame10e12_01_23.Laboratorio;

import java.util.ArrayList;

public class TemperatureSensorImpl implements TemperatureSensor {
	
	private final int id;
	private Double temperature;
	private ArrayList<TemperatureObserver> observers;
	private Thread reader;
	private SimpleThreadPool threadPool;
	
	public TemperatureSensorImpl(int id, int count) {
		if (count <= 1) throw new IllegalArgumentException("count <= 1");
		
		this.id = id;
		this.temperature = null;
		this.observers = new ArrayList<>();
		this.threadPool = new SimpleThreadPool(count);
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public double getTemperature() {
		synchronized (this) {
			return temperature;
		}
	}

	@Override
	public void start() {
		reader = new Thread(() -> {
			while(true) {
				readFromSensor();
				notifyObservers();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(Thread.currentThread().getName() + " interrupted while waiting for a new temperature");
					return;
				}
			}
		});
		
		System.out.println("Starting sensor: " + id);
		reader.start();
	}

	private void readFromSensor() {
		synchronized (this) {
			// genero una temperatura randomica tra 0 e 99.9
			temperature = Math.random()*100; 
		}
	}

	private void notifyObservers() {
		for (TemperatureObserver observer: observers) {
			threadPool.execute(() -> observer.update(this));
		}
	}

	@Override
	public void stop() {
		System.out.println("Stopping sensor: " + id);
		
		reader.interrupt();
		threadPool.shutdown();
	}

	@Override
	public void attach(TemperatureObserver o) {
		synchronized (this) {
			observers.add(o);
		}
	}

	@Override
	public void detach(TemperatureObserver o) {
		synchronized (this) {
			observers.remove(o);
		}
	}

}
