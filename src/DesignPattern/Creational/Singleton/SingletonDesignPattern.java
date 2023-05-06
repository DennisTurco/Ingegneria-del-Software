package DesignPattern.Creational.Singleton;


/*
Goal:
	Singleton Pattern says that just
	"define a class that has only one instance and provides a global point of access to it".

	In other words, a class must ensure that only single instance should be created and single object 
	can be used by all other classes.
*/



//------------------------- TIPO 1
class Singleton {
	private static Singleton instance; 
  
	private Singleton(){}
  
	public static Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
    
		return instance;
	}
}


//------------------------- TIPO 2
class SingletonSynchronizedMethod {
	private static SingletonSynchronizedMethod instance; 
  
	  private SingletonSynchronizedMethod(){}
	  
	  public static synchronized SingletonSynchronizedMethod getInstance() {
		  if(instance == null) {
			  instance = new SingletonSynchronizedMethod();
		  }
		  
		  return instance;
	  }
}


//------------------------- TIPO 3
class SingletonSynchronized {
	private static SingletonSynchronized instance; 
  
	private SingletonSynchronized(){}
  
	public static SingletonSynchronized getInstance() {
		if(instance == null) {
			synchronized (SingletonSynchronized.class) {
				if(instance == null) {
					instance = new SingletonSynchronized();
				}
			}
		}
		
		return instance;
	}
}


//-------------------------
public class SingletonDesignPattern {

	public static void main(String[] args) {
		Singleton instance = Singleton.getInstance();
    
		System.out.println(instance);
    
		Singleton instance1 = Singleton.getInstance();
    
		System.out.println(instance1);
	}
}
