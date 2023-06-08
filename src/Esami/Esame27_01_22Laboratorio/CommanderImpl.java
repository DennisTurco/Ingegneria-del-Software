package Esami.Esame27_01_22Laboratorio;

public class CommanderImpl implements Commander {
	
	private static CommanderImpl instance; 
	
	private CommanderImpl() {
		// blank
	}
	
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
		
		new Thread(() -> {
			slave.executePartB();
			synchronized (this) {
				this.notify();
			}
			slave.executePartC();
		}).start();
		
		slave.executePartA();
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return slave.finish() == 0;
	}
}