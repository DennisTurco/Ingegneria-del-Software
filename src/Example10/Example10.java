package Example10;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import Example09.examples.model.Student;

public class Example10 {

    // primo argomento è il proxy, in questo caso l'oggetto studente
	// il secondo argomento è il messaggio inteso come quale messaggio (messsaggio che ci è stato inviato)
	// il terzo argomento ci dice gli array di argomenti che ci arrivano insieme al messaggio, in questo caso non serve perchè non contiene informazioni
    private Object handler(Object proxy, Method method, Object[] arguments) throws Throwable {
        String methodName = method.getName();

        switch(methodName) {
            case "getID":
                return 1;
            case "getSurname":
                return "Verdi";
            case "getName":
                return "Giuseppe";
            default:
                throw new IllegalArgumentException("unsupported " + method.getName());
        }
    }
    
    public void go() {

        // utilizziamo la classe Proxy (non nostra) con il metodo newProxyInstance
		// la pima cosa che passiamo è un classLoader che per semplicità gli passiamo quello che stiamo usando in questo momento (oggetto classLoader usato per caricare la classe)
		// poi passiamo un array di classi usando il punto interrogativo perchè non sappiamo il tipo e lo riempiamo con un unico oggetto "student.class" che è il descrittore della classe studente
		// come ultimo argomento passiamo un istanza del metodo handler (this::handle = utilizza il metodo handler dell'oggetto this per costruire un implementazione dell'interfaccia invocation handler che non ha ambiguità perchè contiene un metodo solo)
        
        // newProxyInstance permette di costruire un proxy che implementa le interfacce che usiamo
        // prende come argomenti: newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
        Student student = (Student) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] { Student.class }, this::handler); 
        
        System.out.println("ID: " + student.getID());
        System.out.println("Name: " + student.getName());
        System.out.println("Surname: " + student.getSurname());
        System.out.println();
    }

    public static void main(String[] args) {
        new Example10().go();
    }
}
