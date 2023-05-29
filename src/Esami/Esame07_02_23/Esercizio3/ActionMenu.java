package Esami.Esame07_02_23.Esercizio3;

public class ActionMenu {
	private Action commandUndo;
	private Action commandExecute;
	
	public ActionMenu(SimpleStack stack) {
		commandUndo = new ActionUndo(stack);
		commandExecute = new ActionExecute(stack);
	}
	
	public void undo() {
		commandUndo.command();
	}
	
	public void execute() {
		commandExecute.command();
	}
}