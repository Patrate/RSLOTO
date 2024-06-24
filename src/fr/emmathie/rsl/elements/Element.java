package fr.emmathie.rsl.elements;

import java.io.Serializable;

public abstract class Element implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4509083027156612231L;
	private String name;
	
	public Element() {
		this("");
	}
	
	public Element(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	
	public abstract boolean execute();
	
	@Override
	public String toString() {
		return "Element [name=" + getName() + ", class=" + this.getClass().getSimpleName() + "]";
	}
}
