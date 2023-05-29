package Esami.Esame07_02_23.Esercizio3;

import java.util.LinkedList;
import java.util.List;

public class SimpleStack implements Stack {
	
	private List<Object> list;
	
	public SimpleStack() {
		this.list = new LinkedList<>();
	}
	
	@Override
	public void push(Object value) {
		if (value == null) throw new IllegalArgumentException("value == null");
		list.add(value);
	}

	@Override
	public Object pop() throws EmptyStackException {
		if (list.size() == 0) throw new EmptyStackException("list.size() == 0");
		
		return list.remove(list.size()-1);
	}

}
