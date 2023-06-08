package DesignPattern.Behavioral.Iterator;

import java.util.ArrayList;
import java.util.List;

interface Iterator {
	public boolean hasNext();
	public Object next();
}

interface Container {
	public Iterator getIterator();
}

class ContainerImpl implements Container {
	
	private List<String> list;
	
	public ContainerImpl() {
		this.list = new ArrayList<>();
	}
	
	@Override
	public Iterator getIterator() {
		return new InnerIterator();
	}
	
	public void add(String word) {
		list.add(word);
	}
	
	// INNER CLASS
	class InnerIterator implements Iterator {
		
		private int index;
		
		public InnerIterator() {
			this.index = 0;
		}
		
		@Override
		public boolean hasNext() {
			if (index < list.size()) return true;
			else return false;
		}

		@Override
		public Object next() {
			if (this.hasNext()) return list.get(index++);
			else return null;
		}	
	}
}


public class IteratorDesignPattern {
	public static void main(String[] args) {
		ContainerImpl container = new ContainerImpl();
		
		for (int i=0; i<10; ++i) {
			container.add("" + i);
		}
		
		for (Iterator iter = container.getIterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
	}
}
