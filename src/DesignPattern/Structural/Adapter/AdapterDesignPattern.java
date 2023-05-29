package DesignPattern.Structural.Adapter;

/*
Goal:
	An Adapter Pattern says that just "converts the interface of a class into another interface that a client wants".

	In other words, to provide the interface according to client requirement while using the services of a class with 
	a different interface.
*/

//--------------------
interface WebDriver {
	public void getElement();
	public void selectElement();
}

//--------------------
class ChromeDriver implements WebDriver {
	@Override
	public void getElement() {
		System.out.println("Get element from ChromeDriver");
	}

	@Override
	public void selectElement() {
		System.out.println("Select element from ChromeDriver"); 
	}
}

// --------------------
class IEDriver {
	public void findElement() {
		System.out.println("Find element from IEDriver");
	}

	public void clickElement() {
		System.out.println("Click element from IEDriver");
	}
}

//--------------------
class WebDriverAdapter implements WebDriver {

	private IEDriver ieDriver;
  
	public WebDriverAdapter(IEDriver ieDriver) {
		this.ieDriver = ieDriver;
	}
	
	@Override
	public void getElement() {
	    ieDriver.findElement();
	    
	}
	
	@Override
	public void selectElement() {
		ieDriver.clickElement();
	}
}

//--------------------
public class AdapterDesignPattern {

	public static void main(String[] args) {
	    ChromeDriver a = new ChromeDriver();
	    a.getElement();
	    a.selectElement();
	    
	    IEDriver e = new IEDriver();
	    e.findElement();
	    e.clickElement();
	    
	    WebDriver wID = new WebDriverAdapter(e);
	    wID.getElement();
	    wID.selectElement();  
  }
}