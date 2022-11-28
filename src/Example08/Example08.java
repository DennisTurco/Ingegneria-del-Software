package Example08;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Scanner;

public class Example08 {
	
	// metto il punto interrogativo perchè non so il tipo a tempo di compilazione
	private void dump(Class<?> clazz) {
		System.out.println("Class: " + clazz.getName());
		
		Class<?> baseClass = clazz.getSuperclass();
		
		if (baseClass != null) System.out.println("Base Class: " + clazz.getName());
		
		Class<?>[] interfaces = clazz.getInterfaces();
		
		for (int i=0; i<interfaces.length; ++i) {
			System.out.println("Implemented Interface: " + interfaces[i].getName());
		}
		
		Field[] fields = clazz.getFields();
		
		for (int i=0; i<fields.length; ++i) {
			Field field = fields[i];
			Class<?> fieldClass = field.getType();
			System.out.println("Field: " + fieldClass.getName() + " " + field.getName());
		}
		
		Constructor<?>[] constructors = clazz.getConstructors();
		
		for (int i=0; i<constructors.length; ++i) {
			Constructor<?> constructor = constructors[i];
			
			System.out.println("Constructor: " + constructor.getName() + "(");
			
			Parameter[] parameters = constructor.getParameters();
			
			for (int j=0; j<parameters.length; j++) {
				Parameter parameter = parameters[j];
				
				Class<?> parametersClass = parameter.getType();
				
				System.out.println(parametersClass.getName() + " " + parameter.getName());
				
				if(j != parameters.length - 1) {
					System.out.println(", ");
				}
				
				//.....
			}
		}
		
	}
	
	private void show(String className) {
		
	}
	
	private void go() {
		try {
			Scanner scanner = new Scanner(System.in);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
	
	public static final void main() {
		new Example08().go();
	}
}
