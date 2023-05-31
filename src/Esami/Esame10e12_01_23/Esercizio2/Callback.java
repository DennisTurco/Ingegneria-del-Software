package Esami.Esame10e12_01_23.Esercizio2;

public interface Callback<T> {
	public void onSuccess(T result);
	
	public default void onFailure(Throwable exception) {
		exception.printStackTrace();
	}
}