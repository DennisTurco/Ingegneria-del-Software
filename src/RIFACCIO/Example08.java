package RIFACCIO;

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

            System.out.print("insert class name: ");

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

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void dump(Class<?> clazz) {
        System.out.println("Class: " + clazz.getName());

        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i=0; i<interfaces.length; ++i) {
            System.out.println("Interface: " + interfaces[i].getName());
        }

        Field[] fields = clazz.getFields();
        for (int i=0; i<fields.length; ++i) {
            System.out.println("Field: " + fields[i].getName());
        }

        Constructor<?>[] constructor = clazz.getConstructors();
        for (int i=0; i<constructor.length; ++i) {
            System.out.print("Contructor: " + constructor[i].getName() + "(");
            Parameter[] parameter = constructor[i].getParameters();
            for (int j=0; j<parameter.length; ++j) {
                System.out.print(parameter[j].getType() + parameter[j].getName());

                if (j < parameter.length-1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }

        Method[] methods = clazz.getMethods();
        for (int i=0; i<methods.length; ++i) {
            System.out.print("Method: " + methods[i].getName() + "(");
            Parameter[] parameter = methods[i].getParameters();
            for (int j=0; j<parameter.length; ++j) {
                System.out.print(parameter[j].getType() + parameter[j].getName());

                if (j < parameter.length-1) {
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
