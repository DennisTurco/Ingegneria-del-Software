package Esami.Esame27_01_22Laboratorio;

import java.util.ArrayList;
import java.util.List;

public class SlaveManager {
	private static SlaveManager instance;
	private List<Slave> list;
	
	private SlaveManager(int count) {
		this.list = new ArrayList<>();
		
		for (int i=0; i<count; ++i) {
			list.add(new SlaveImpl());
		}
	}
	
	// singleton
	public static SlaveManager getInstance(int count) {
		if (instance == null) {
			synchronized (SlaveManager.class) {
				if (instance == null) {
					instance = new SlaveManager(count);
				}
			}
			
		}
		return instance;
	}
	
	public List<Slave> getList() {
		return list;
	}
}
