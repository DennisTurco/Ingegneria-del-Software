package it.unipr.informatica.concurrent;

public interface Callback<Type> {
	public void onSuccess(Type resul);
	
	// e' possibile anche mettere un corpo ad un metodo di un interfaccia ma va aggiunto default (attenzione non ha un stato pero')
	public default void onFailure(Throwable throwable) {
		throwable.printStackTrace();
	}
}
