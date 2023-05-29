

/*
 * Command is a behavioral design pattern that turns a request into a 
 * stand-alone object that contains all information about the request. 
 * This transformation lets you pass requests as a method arguments, 
 * delay or queue a request’s execution, and support undoable operations.
*/


package DesignPattern.Behavioral.Command;

// --------------------- interface that will act as a Command
interface ActionListenerCommand {
	public void execute();
}

// --------------------- class that will act as a Receiver
class Document {
	public void open() {
		System.out.println("Document Opened");
	}
	
	public void save() {
		System.out.println("Document Saved");
	}
}

// --------------------- class that will act as an ConcreteCommand
class ActionOpen implements ActionListenerCommand {
	private Document document;
	
	public ActionOpen(Document document) {
		this.document = document;
	}
	
	@Override
	public void execute() {
		document.open();
	}
}

// --------------------- class that will act as an ConcreteCommand
class ActionSave implements ActionListenerCommand {
	private Document document;
	
	public ActionSave(Document document) {
		this.document = document;
	}

	@Override
	public void execute() {
		document.save();
	}
}

// --------------------- class that will act as an Invoker.
class MenuOptions {
	private ActionListenerCommand openCommand;
	private ActionListenerCommand saveCommand;
	
	public MenuOptions(ActionListenerCommand openCommand, ActionListenerCommand saveCommand) {
		this.openCommand = openCommand;
		this.saveCommand = saveCommand;
	}

	public void clickOpen() {
		openCommand.execute();
	}

	public void clickSave() {
		saveCommand.execute();
	}
}

// --------------------- class that will act as a Client
public class CommandDesignPattern {
	public static void main(String[] args) {
		Document document = new Document();
		
		ActionListenerCommand clickOpen = new ActionOpen(document);
		ActionListenerCommand clickSave = new ActionSave(document);
		
		MenuOptions menu = new MenuOptions(clickOpen, clickSave);  
        
        menu.clickOpen();  
        menu.clickSave(); 
	}
}
