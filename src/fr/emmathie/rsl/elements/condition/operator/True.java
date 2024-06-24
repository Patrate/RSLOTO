package fr.emmathie.rsl.elements.condition.operator;

import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class True extends CondElement implements Condition{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;
	
	public True() {
	}

	@Override
	public boolean isTrue() {
		return true;
	}

}
