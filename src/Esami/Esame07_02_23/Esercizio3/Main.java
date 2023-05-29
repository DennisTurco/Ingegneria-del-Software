package Esami.Esame07_02_23.Esercizio3;

public class Main {
	public static void main(String[] args) {
		SimpleStack stack = new SimpleStack();
		
		ActionMenu action = new ActionMenu(stack);
		
		action.execute();
		action.execute();
		action.execute();
		action.execute();
		action.execute();
		action.execute();
		
		action.undo();
		action.undo();
		action.undo();
		action.undo();
		action.undo();
		action.undo();
	}
}
