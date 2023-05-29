package Esami.Esame07_02_23.Esercizio3;

public class ActionExecute implements Action {
	
	private Stack receiver;
	
	public ActionExecute(Stack stack) {
		this.receiver = stack;
	}
	
	@Override
	public void command() {
		Object object = new Object();
		
		receiver.push(object);
		System.out.println("Executing command: " + object);
	}

}
