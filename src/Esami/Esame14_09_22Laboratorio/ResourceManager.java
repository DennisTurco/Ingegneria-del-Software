package Esami.Esame14_09_22Laboratorio;

public interface ResourceManager {
	public void acquireResources(int i, int j);	// acquisisce le risorse i e j
	public void freeResource(int i, int j);		// libera le risorse i e j
}