package Esami.Esame07_02_23.Laboratorio;

import java.util.ArrayList;

public class Main {
	
	private static final int N = 10;
	private static final int M = 100;
	
	public static void main(String[] args) {
		
		// creazione oggetti
		ActorResourceFactory factory = ActorResourceFactory.getInstance();
		
		ArrayList<Actor> actors = new ArrayList<>(N);
		ArrayList<Resource> resources = new ArrayList<>(M);
		
		// creazione risorse
		for (int i=0; i<M; ++i) {
			resources.add(factory.createResorce(i));
		}
		
		// creazione attori
		for (int i=0; i<N; ++i) {
			actors.add(factory.createActor(resources, actors));
		}
		
		// attesa
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//stop dei threads
		for (int i=0; i<N; ++i) {
			actors.get(i).stop();
		}

		
		System.out.println("Terminated");
	}
}
