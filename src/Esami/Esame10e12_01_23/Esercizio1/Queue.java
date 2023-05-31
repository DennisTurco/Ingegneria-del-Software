package Esami.Esame10e12_01_23.Esercizio1;

import java.util.NoSuchElementException;

public interface Queue {
	public boolean isEmpty();
	public void push(Object o);
	public Object pop() throws NoSuchElementException;
}