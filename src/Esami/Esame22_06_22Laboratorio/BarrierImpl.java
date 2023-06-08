package Esami.Esame22_06_22Laboratorio;

import java.util.LinkedList;
import java.util.List;

public class BarrierImpl implements Barrier {
	
	private List<Object> list;
	
	public BarrierImpl() {
		list = new LinkedList<>();
	}
	
	@Override
	public void add(Object object) {
		list.add(object);
		
		System.out.println("Adding element: " + object.getClass().getName());
	}

	@Override
	public void remove(Object object) {
		list.remove(object);
		
		System.out.println("Removing element: " + object.getClass().getName());
	}

	@Override
	public void await() throws InterruptedException {
		synchronized (this) {
			this.wait();
		}
	}

}
