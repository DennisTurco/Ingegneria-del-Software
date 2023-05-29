package Esami.Esame07_02_23.Laboratorio;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final int N = 10;
	private static final int M = 100;
	
	private void go() {
		ActorResourceFactory factory = ActorResourceFactory.getInstance();
		
		List<Actor> actors = new ArrayList<>(N);
		List<Resource> resources = new ArrayList<>(M);
		
		
		// prima creo le risorse
		for (int i=0; i<M; ++i) {
			resources.add(factory.createResource(i));
		}
		
		// poi gli actors (gli actors lavorano sulle risorse)
		for (int i=0; i<N; ++i) {
			actors.add(factory.createActor(resources, actors));
		}
		
		// sleep di un minuto :(
		try {
			Thread.sleep(1000 * 60);
			System.out.println("Terminated");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
}
