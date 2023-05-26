package Esami.Esame10_01_23.Esercizio2;

public class Example {
	
	private void go() {
		AImpl imp = new AImpl();
		
		Callback<Integer> callback = new Callback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				return;
			}
		};
		
		imp.m(2000, 4000, callback);

	}
	
	public static void main(String[] args) {
		new Example().go();
	}
}
