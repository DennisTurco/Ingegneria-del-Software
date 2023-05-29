package Esami.Esame07_02_23.Laboratorio;

import java.util.List;

public class ActorResourceFactory {
	
	private static ActorResourceFactory instance;
	
	private ActorResourceFactory() {
		// blank
	}
	
	public static ActorResourceFactory getInstance() {
		if (instance == null) {
			instance = new ActorResourceFactory();
		}
		
		return instance;
	}
	
	public Actor createActor(List<Resource> resources, List<Actor> actors) {
		return new Actor(resources, actors);
	}
	
	public Resource createResource(int ID) {
		return new ResourceImpl(ID);
	}
}