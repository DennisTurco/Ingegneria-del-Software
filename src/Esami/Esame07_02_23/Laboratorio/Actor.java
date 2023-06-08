package Esami.Esame07_02_23.Laboratorio;

import java.util.List;

public class Actor {
	
	private Thread thread;
	private List<Resource> resources;
	private List<Actor> actors;
	
	public Actor(List<Resource> resources, List<Actor> actors) {
		this.resources = resources;
		this.actors = actors;
		
		mainCicle();
	}
	
	public void deliver(Message message) {
		if (message.getValue() <= 0) return;
		
		synchronized (this) {
			Actor actor;
			do {
				int random = (int) (Math.random()*actors.size());
				actor = actors.get(random);
			} while (actor == this);
			
			System.out.println("delivering message: " + (message.getValue()-1));
			actor.deliver(new Message(message.getValue()-1));
			
		}
	}
	
	private void mainCicle() {
		thread = new Thread(() -> {
			while (true) {	
				try {
					// aquisisco K risorse
					int random = (int) (Math.random()*10);
					for (int i=0; i<random; ++i) {
						resources.get(i).acquire();
					}
					
					// use
					int somma = 0;
					for (int i=0; i<random; ++i) {
						somma += resources.get(i).use();
					}
					
					// attesa
					Thread.sleep(somma);
					
					// rilascio
					for (int i=0; i<random; ++i) {
						resources.get(i).release();
					}
					
					deliver(new Message(somma));
				} catch (InterruptedException e) {
					System.err.println(Thread.currentThread().getName() + " terminated while doing his Actor job");
					return;
				}
			}
		});
		
		thread.start();
	}
	
	public void stop() {
		thread.interrupt();
	}
}
