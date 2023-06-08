package Esami.Esame27_01_22Laboratorio;

import java.util.ArrayList;
import java.util.List;

public class SlaveManager {
	
	private static SlaveManager instance;
	private List<SlaveImpl> slaves;
	
	private SlaveManager() {
		// blank
	}
	
	private SlaveManager(int slaves) {
		if (slaves <= 0) throw new IllegalArgumentException("slaves <= 0"); 
			
		this.slaves = new ArrayList<>(slaves);
		for (int i=0; i<slaves; ++i) {
			this.slaves.add(new SlaveImpl());
		}
	}
	
	// singleton
	public static SlaveManager getInstance(int slaves) {
		if (instance == null) {
			synchronized (SlaveManager.class) {
				if (instance == null) {
					instance = new SlaveManager(slaves);
				}
			}
		}
		return instance;
	}
	
	public List<SlaveImpl> getSlaves() {
		return slaves;
	}
	
	
}
