package it.unipr.informatica.concurrent;

// 9.12
// ci sono alcune classi che in qualche modo la v.m riesce a trasformarli in array di byte ...
// una classe è serializzabile se implementa ....
public class RejectedExecutionExeption extends RuntimeException{
	
	// attributo generator automaticamnte per risolvere il warning
	private static final long serialVersionUID = -2564074008578220169L;

	public RejectedExecutionExeption(String message) {
		super(message);
	}
}
