package fr.emmathie.rsl.elements;

import java.io.Serializable;

public class Variable<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6919558989789113682L;
	
	private String name;
	private T value;
	
	public enum VTYPE {
		EMPTY, NUMBER, STRING, URL;
	}
	
	public Variable(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T s) {
		this.value = s;
	}
	
	@Override
	public String toString() {
		return this.getValue().toString();
	}
}
