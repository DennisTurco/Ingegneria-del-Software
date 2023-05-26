package DesignPattern.Creational.Builder;

/*
Goal:
	Builder pattern aims to “Separate the construction of a complex object from its representation so that 
	the same construction process can create different representations.” It is used to construct a complex 
	object step by step and the final step will return the object. The process of constructing an object 
	should be generic so that it can be used to create different representations of the same object.
*/

//-------------------------------- generic interface
interface HousePlan {
	public void setBasement(String name);
	public void setStructure(String name);
	public void setRoof(String name);
	public void setInterior(String name);
	public String printHouse();
}

//-------------------------------- generic interface
interface HouseBuilder {
	public void buildBasement();
	public void buildStructure();
	public void buildRoof();
	public void buildInterior();
	public House getHouse();
	public String printHouse();
}

//--------------------------------
class House implements HousePlan {
	private String basement;
	private String structure;
	private String roof;
	private String interior; 

	@Override
	public void setBasement(String name) {	
		this.basement = name;
	}

	@Override
	public void setStructure(String name) {
		this.structure = name;
	}

	@Override
	public void setRoof(String name) {
		this.roof = name;
	}

	@Override
	public void setInterior(String name) {
		this.interior = name;
	}
	
	@Override
	public String printHouse() {
		return (basement + " " + structure + " " + roof + " " + interior);
	}
}

//--------------------------------
class IglooHouseBuilder implements HouseBuilder {
	private House house;
	
	public IglooHouseBuilder() {
		this.house = new House();
	}

	@Override
	public void buildBasement() {
		house.setBasement("Ice Bars");
	}

	@Override
	public void buildStructure() {
		house.setStructure("Ice Blocks");
	}

	@Override
	public void buildRoof() {
		house.setInterior("Ice Carvings");
	}

	@Override
	public void buildInterior() {
		house.setRoof("Ice Dome");
	}
	
	@Override
	public House getHouse() {
		return house;
	}
	
	@Override
	public String printHouse() {
		return house.printHouse();
	}
}

//--------------------------------
class TipiHouseBuilder implements HouseBuilder {
    private House house;
    
    public TipiHouseBuilder() {
        this.house = new House();
    }
    
    @Override
    public void buildBasement() {
        house.setBasement("Wooden Poles");
    }
    
    @Override
    public void buildStructure() {
        house.setStructure("Wood and Ice");
    }
    
    @Override
    public void buildInterior() {
        house.setInterior("Fire Wood");
    }
    
    @Override
    public void buildRoof() {
        house.setRoof("Wood, caribou and seal skins");
    }
    
    @Override
    public House getHouse() {
        return house;
    }
    
    @Override
	public String printHouse() {
		return house.printHouse();
	}
}

//--------------------------------
class CivilEngineer {
    private HouseBuilder houseBuilder;
    
    public CivilEngineer(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }
    
    public House getHouse() {
        return houseBuilder.getHouse();
    }
    
    public void constructHouse() {
        houseBuilder.buildBasement();
        houseBuilder.buildStructure();
        houseBuilder.buildRoof();
        houseBuilder.buildInterior();
    }
}


// -------------------------------- example
public class BuilderDesignPattern {
	private void go() {
		HouseBuilder iglooBuilder = new IglooHouseBuilder();
        CivilEngineer engineer = new CivilEngineer(iglooBuilder);
        engineer.constructHouse();
        
        House house = engineer.getHouse();
        System.out.println("Builder constructed: " + house);
        System.out.println("Builder constructed: " + house.printHouse());
	}
	
	public static void main(String[] args) {
		new BuilderDesignPattern().go();
	}
}
