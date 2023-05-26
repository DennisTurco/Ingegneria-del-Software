package Esami.Esame27_01_22Laboratorio;

public class CommanderImpl implements Commander {

	private static CommanderImpl instance;
	
	private CommanderImpl() {
		// blank
	}
	
	// singleton
	public static CommanderImpl getInstance() {
		if (instance == null) {
			synchronized (CommanderImpl.class) {
				if (instance == null) {
					instance = new CommanderImpl();
				}
			}
		}
		return instance;
	}
	
	@Override
	public boolean command(Slave slave) {
		
		// attivo il secondo Thread
		// percorso Thread 2		
		new Thread(() -> {
			slave.executePartB();
			
			synchronized (this) {
				this.notify();
			}
			
			slave.executePartC();
		}).start();
		
		
		// percorso Thread 1 (percorso corrente)
		slave.executePartA();
		
		synchronized (this) {
			try {
				this.wait();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int result = slave.finish();
		
		if (result == 0) return true;
		else return false;
		
	}

}
