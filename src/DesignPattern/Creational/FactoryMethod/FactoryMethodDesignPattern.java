package DesignPattern.Creational.FactoryMethod;

/*
Goal: 
	A Factory Pattern or Factory Method Pattern says that just define an interface or abstract class for creating 
	an object but let the subclasses decide which class to instantiate. 
	
	In other words, subclasses are responsible to create the instance of the class.
*/


// ------------------------- Interface
interface Notification {
	public void notifyUser();
}


// ------------------------- class1
class SMSNotification implements Notification {
	@Override
	public void notifyUser() {
		System.out.println("Sending an SMS notification");
	}	
}

// ------------------------- class2
class EmailNotification implements Notification {
	@Override
	public void notifyUser() {
		System.out.println("Sending an e-mail notification");
	}
}

// ------------------------- class3
class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending a push notification");
    }
}

// ------------------------- factory class
class NotificationFactory {
	
	private NotificationFactory() {
		// blank
	}
	
	public static Notification createNotification(String channel) {
		if (channel == null || channel.isEmpty()) return null;
		
		if (channel.equals("SMS")) return new SMSNotification();
		if (channel.equals("EMAIL")) return new EmailNotification();
		if (channel.equals("PUSH")) return new PushNotification();
		
		throw new IllegalArgumentException("Unknow channel " + channel);
	}
}


public class FactoryMethodDesignPattern {
	public static void main(String[] args) {
		
		Notification notification = NotificationFactory.createNotification("SMS");
		notification.notifyUser();
	}
}
