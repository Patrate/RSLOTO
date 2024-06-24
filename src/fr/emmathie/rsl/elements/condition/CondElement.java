package fr.emmathie.rsl.elements.condition;

import fr.emmathie.rsl.elements.Element;

public abstract class CondElement extends Element implements Condition{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	public CondElement() {
	}
	
	@Override
	public boolean execute() {
		return isTrue();
	}

}
