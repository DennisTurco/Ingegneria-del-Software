package Esami.Esame10e12_01_23.Laboratorio;

import java.util.LinkedList;
import java.util.List;

public class TemperatureSensorImpl implements TemperatureSensor {
	
	private final int ID;
	private double temperature;
	private List<TemperatureObserver> observers;
	private ExecutorService threadPool;
	private final Detector detector;
	
	public TemperatureSensorImpl(int ID, int count) {
		if (ID <= 1) throw new IllegalArgumentException("ID <= 1");
		
		this.ID = ID;
		this.temperature = 0;
		this.observers = new LinkedList<>(); 
		this.threadPool = new SimpleThreadPool(count);
		this.detector = new Detector();
	}
	
	
	@Override
	public int getID() {
		// non mi serve la synchronized perche' ID e' final
		return ID;
	}

	@Override
	public double getTemperature() {
		synchronized (this) {
			return temperature;
		}
	}

	@Override
	public void start() {
		System.out.println("starting sensor: " + ID);	
		
		detector.start();
	}

	@Override
	public void stop() {
		System.out.println("stopping sensor: " + ID);
		
		detector.shutdown();
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
	
	private void setTemperature(double temperature) {
		synchronized (this) {
			this.temperature = temperature;
		}
	}
	
	private void notifyObservers() {
		for (TemperatureObserver observer: observers) {
			threadPool.execute(() -> {
				observer.update(this);
			});
		}
	}
	
	
	// INNER CLASS 
	private class Detector implements Runnable {
		
		private Thread thread;
		
		private Detector() {
			this.thread = new Thread(this);
		}
		
		@Override
		public void run() {
			while(true) {
				double temperature = Math.random()*100;
				
				setTemperature(temperature);
				notifyObservers();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		
		private void start() {
			thread.start();
		}
		
		private void shutdown() {
			thread.interrupt();
		}
		
	}

}
