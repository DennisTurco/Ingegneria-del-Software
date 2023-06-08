package DesignPattern.Structural.Decorator;

interface Pizza {
	public String getDescription();
	public double getCost();
}

class PlainPizza implements Pizza {
	@Override
	public String getDescription() {
		return "Thin Dough";
	}

	@Override
	public double getCost() {
		return 4.00;
	}
}

abstract class ToppingDecorator implements Pizza {
	protected Pizza tempPizza;
	
	public ToppingDecorator(Pizza pizza) {
		this.tempPizza = pizza;
	}
	
	public String getDescription() {
		return tempPizza.getDescription();
	}
	
	public double getCost() {
		return tempPizza.getCost();
	}
}

class Mozzarella extends ToppingDecorator {
	public Mozzarella(Pizza pizza) {
		super(pizza);
		
		System.out.println("Adding Dough");
		
		System.out.println("Adding Mozzarella");
	}
	
	public String getDescription() {
		return tempPizza.getDescription() + ", Mozzarella";
	}
	
	public double getCost() {
		return tempPizza.getCost() + .50;
	}
}

class TomatoSauce extends ToppingDecorator {
	public TomatoSauce(Pizza pizza) {
		super(pizza);
		
		System.out.println("Adding Sauce");
	}
	
	public String getDescription() {
		return tempPizza.getDescription() + ", Tomato Sauce";
	}
	
	public double getCost() {
		return tempPizza.getCost() + 0.35;
	}
}


public class DecoratorDesignPattern {
	public static void main(String[] args) {
		Pizza basicPizza = new TomatoSauce(new Mozzarella(new PlainPizza()));
	
		System.out.println("Ingredients: " + basicPizza.getDescription());
		System.out.println("Cost: " + basicPizza.getCost());
	}
}
