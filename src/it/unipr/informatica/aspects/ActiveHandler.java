package it.unipr.informatica.aspects;

public interface ActiveHandler<T extends Active<?>> {
	public T get();
	public void shutdown();
}
