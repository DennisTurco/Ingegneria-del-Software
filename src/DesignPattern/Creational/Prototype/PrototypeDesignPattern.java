package DesignPattern.Creational.Prototype;

interface Animal extends Cloneable {
	public Animal makeCopy();
}

class Sheep implements Animal {
	public Sheep(){
		System.out.println("Sheep is Made");
	}
	
	public Animal makeCopy() {	
		Sheep sheepObject = null;
		
		try {
			sheepObject = (Sheep) super.clone();
		} catch (CloneNotSupportedException e) {  
			System.out.println("The Sheep was Turned to Mush");
			e.printStackTrace();  
		}	
		
		return sheepObject;
	}
	
	public String toString(){
		return "Dolly is my Hero, Baaaaa";
	}
}

class CloneFactory {
	public Animal getClone(Animal animalSample) {
		return animalSample.makeCopy();	
	}
}

public class PrototypeDesignPattern {
	public static void main(String[] args) {
		CloneFactory animalMaker = new CloneFactory();
		Sheep sally = new Sheep();
		Sheep clonedSheep = (Sheep) animalMaker.getClone(sally);
		
		System.out.println(sally);
		System.out.println(clonedSheep);
		System.out.println("Sally HashCode: " + System.identityHashCode(System.identityHashCode(sally)));
		System.out.println("Clone HashCode: " + System.identityHashCode(System.identityHashCode(clonedSheep)));
	}
}
