package Esami.Esame07_02_23.Esercizio3;

public class ActionUndo implements Action {
	
	private Stack receiver;
	
	public ActionUndo(Stack receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void command() {
		try {
			Object result = receiver.pop();
			System.out.println("Received: " + result);
		} catch (EmptyStackException e) {
			e.printStackTrace();
		}
	}
}