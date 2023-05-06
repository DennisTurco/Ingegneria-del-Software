package DesignPattern.Structural.Bridge;

/*
Goal:
	A Bridge Pattern says that just 
	"decouple the functional abstraction from the implementation so that the two can vary independently".
*/


//--------------------
abstract class BridgeDesignPattern {
	Remote remote;
	
	BridgeDesignPattern(Remote remote) {
		this.remote = remote;
	}
	
	abstract void on();
	abstract void off();
}

//--------------------
class Sony extends BridgeDesignPattern {
	Remote remoteType;
	
	Sony(Remote remote) {
		super(remote);
		this.remoteType = remote;
	}
	
	public void on() {
		System.out.println("Sony TV ON: ");
		remoteType.on();
	}
	
	public void off() {
		System.out.println("Sony TV OFF: ");
		remoteType.off();
	}
}


//--------------------
class Philip extends BridgeDesignPattern {
	Remote remoteType;
	
	Philip(Remote remote) {
		super(remote);
		this.remoteType = remote;
	}
	
	public void on() {
		System.out.println("Philip TV ON: ");
		remoteType.on();
	}
	
	public void off() {
		System.out.println("Philip TV OFF: ");
		remoteType.off();
	}
}

//--------------------
interface Remote {
	public void on();
	public void off();
}

//--------------------
class OldRemote implements Remote {
	@Override
	public void on() {
		System.out.println("ON with Old Remote");
	}

	@Override
	public void off() {
		System.out.println("OFF with Old Remote");
	}
}

//--------------------
class NewRemote implements Remote {

	@Override
	public void on() {
		System.out.println("ON with New Remote");
	}

	@Override
	public void off() {
		System.out.println("OFF with New Remote");
	}
}

//--------------------
class BridgeDisignPattern {
	public static void main(String[] args) {
		BridgeDesignPattern sonyOldRemote = new Sony(new OldRemote());
		sonyOldRemote.on();
		sonyOldRemote.off();
		System.out.println();
		
		BridgeDesignPattern sonyNewRemote = new Sony(new NewRemote());
		sonyNewRemote.on();
		sonyNewRemote.off();
		System.out.println();
		
		BridgeDesignPattern philipsOldRemote = new Philip(new OldRemote());
		philipsOldRemote.on();
		philipsOldRemote.off();
		System.out.println();
		
		BridgeDesignPattern philipsNewRemote = new Philip(new NewRemote());
		philipsNewRemote.on();
		philipsNewRemote.off();
		System.out.println();
	}
}