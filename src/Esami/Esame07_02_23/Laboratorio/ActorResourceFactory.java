package Esami.Esame07_02_23.Laboratorio;

import java.util.List;

public class ActorResourceFactory {
	
	private static ActorResourceFactory instance;
	
	private ActorResourceFactory() {
		// blank
	}
	
	// singleton
	public static ActorResourceFactory getInstance() {
		if (instance == null) {
			instance = new ActorResourceFactory();
		}
		return instance;
	}
	
	public ResourceImpl createResorce(int ID) {
		return new ResourceImpl(ID);
	}
	
	public Actor createActor(List<Resource> resources, List<Actor> actors) {
		return new Actor(resources, actors);
	}
}
