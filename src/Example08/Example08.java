package Example08;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Locale;
import java.util.Scanner;

public class Example08 {

    private void go() {
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.useLocale(Locale.US);

            System.out.print("Please, enter the fully qualified name of a class: ");
            
            // serve per fare input da tastiera
            String className = scanner.nextLine();

            show(className);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void show(String className) {
        try {
            // siccome non sappiamo il tipo, mettiamo <?>
            Class<?> clazz = Class.forName(className);

            dump(clazz);
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Cannot load " + className);
        }
    }

    private void dump(Class<?> clazz) {
        // stampo ill nome della classe
        System.out.println("Class: " + clazz.getName());

        // stampo il nome della classe base
        Class<?> baseClass = clazz.getSuperclass();
        if (baseClass != null) System.out.println("Base class: " + baseClass.getName());
        
        // stampo il nome delle interfacce implementate
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i=0; i<interfaces.length; i++) {
            System.out.println("Implemented interface: " + interfaces[i].getName());
        } 

        // stampo il nome dei campi/attributi visibili
        Field[] fields = clazz.getFields();
        for (int i=0; i<fields.length; i++) {
            System.out.println("Field: " + fields[i].getType() + " " + fields[i].getName());
        }

        // stampo i costruttori (e parametri) della classe
        Constructor<?>[] constructors = clazz.getConstructors();
        for (int i=0; i<constructors.length; i++) {
            System.out.print("Constructor: " + constructors[i].getName() + "(");

            // ottengo i parametri
            Parameter[] parameters = constructors[i].getParameters();
            for (int j=0; j<parameters.length; j++) {
                System.out.print(parameters[j].getType() + " " + parameters[j].getName());
                
                if ( j != parameters.length-1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }

        // stampo i metodi (e parametri) della classe
        Method[] methods = clazz.getMethods();
        for (int i=0; i<methods.length; i++) {
            System.out.print("Method: " + methods[i].getName() + "(");

            // ottengo i parametri
            Parameter[] parameters = methods[i].getParameters();
            for (int j=0; j<parameters.length; j++) {
                System.out.print(parameters[j].getType() + " " + parameters[j].getName());
                
                if (j != parameters.length-1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }

    }

    public static void main(String[] args) {
        new Example08().go();
    }
}