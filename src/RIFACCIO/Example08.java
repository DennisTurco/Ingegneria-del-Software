package RIFACCIO;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Locale;
import java.util.Scanner;

public class Example08 {
    private void go() {
        try (Scanner scanner = new Scanner(System.in)){
            scanner.useLocale(Locale.US);

            System.out.println("Please, enter the fully qualified name of a class: ");

            String className = scanner.nextLine();

            show(className);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void show(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            dump(clazz);
        } catch (ClassNotFoundException exception) {
            System.err.println("Cannot load " + className);
        }
    }

    private void dump(Class<?> clazz) {
        System.out.println("Class: " + clazz.getName());

        Class<?> baseClass = clazz.getSuperclass();
        if (baseClass != null) System.out.println("Base class: " + baseClass.getName());

        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i=0; i<interfaces.length; ++i) {
            System.out.println("Implemented interface: " + interfaces[i].getName());
        }

        Field[] fields = clazz.getFields();
        for (int i=0; i<fields.length; ++i) {
            System.out.println("Field: " + fields[i].getType() + " " + fields[i].getName());
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        for (int i=0; i<constructors.length; ++i) {
            System.out.print("Constructor: " + constructors[i].getName() + "(");
            Parameter[] parameters = constructors[i].getParameters();
            for (int j=0; j<parameters.length; ++j) {
                System.out.print(parameters[j].getType() + " " + parameters[j].getName());
                
                if (parameters.length-1 != j) {
                    System.out.print(", ");
                }
            }

            System.out.println(")");
        }

        Method[] methods = clazz.getMethods();
        for (int i=0; i<methods.length; ++i) {
            System.out.print("Method: " + methods[i].getName() + "(");
            Parameter[] parameters = methods[i].getParameters();
            for (int j=0; j<parameters.length; ++j) {
                System.out.print(parameters[j].getType() + " " + parameters[j].getName());
                
                if (parameters.length-1 != j) {
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
