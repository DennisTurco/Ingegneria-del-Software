package Esami.Esame07_02_23.Laboratorio;

import java.util.List;
import java.util.Random;

public class Actor {
	
	private final List<Actor> actors;
	private final Random random;
		
	public Actor(List<Resource> resources, List<Actor> actors) {
		
		this.actors = actors;
		this.random = new Random();
		
		Thread thread = new Thread(() -> {
			
			while (true) {
				int value = random.nextInt(10);
				int sleepTime = 0;
				
				// acquisizione
				for (int i=0; i<value; ++i) {
					try {
						resources.get(i).acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				// utilizzo
				for (int i=0; i<value; ++i) {
					sleepTime = sleepTime + resources.get(i).use();
				}
				
				// sleep
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// rilascio
				for (int i=0; i<value; ++i) {
					resources.get(i).release();
				}
				
				// messaggio
				getAnotherRandomActor().deliver(new Message(sleepTime));
			}
			
		});
		
		thread.start();
	}
	
	public void deliver(Message message) {
		if (message.getValue() < 0) return;
		
		synchronized (this) {
			getAnotherRandomActor().deliver(new Message(message.getValue()-1));
		}
	}
	
	private Actor getAnotherRandomActor() {
		Actor randomActor;
		do {
			int value = random.nextInt(actors.size());
			randomActor = actors.get(value); 
		} while (randomActor == this);
		
		return randomActor;
	}
	
}
