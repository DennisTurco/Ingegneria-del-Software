package Esami.Esame14_01_2022Laboratorio;

public class ResourceManager {
	
	private static ResourceManager instance;
	private ResourceImpl[] resources;
	
	public ResourceManager(int dim) {
		this.resources = new ResourceImpl[dim];
		for (int i=0; i<dim; ++i) {
			resources[i] = new ResourceImpl(i);
		}
	}
	
	// singleton
	public static ResourceManager getInstance(int dim) {
		if (instance == null) {
			synchronized (ResourceManager.class) {
				if (instance == null) {
					instance = new ResourceManager(dim);
				}
			}
		}
		return instance;
	}
	
	public Resource[] acquire(int id) {
		
		// voglio lavorare solo sulle prime 3
		ResourceImpl newResources[] = new ResourceImpl[3];
		
		for (int i=0; i<3; ++i) {
			newResources[i] = resources[(i + id) % resources.length];
		}
		
		synchronized (resources[id]) {
			while (!newResources[0].getFree() || !newResources[1].getFree() || !newResources[2].getFree()) {
				try {
					resources[id].wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			synchronized (resources) {
				newResources[0].setFree(false);
				newResources[1].setFree(false);
				newResources[2].setFree(false);
				
				System.out.println("Risorse acquisite:\nRisorsa1 -> " + newResources[0] + "\nRisorsa2 -> " + newResources[1] + "\nRisorsa3 -> " + newResources[2]);
				return newResources;
			}
		}	
	}
}
