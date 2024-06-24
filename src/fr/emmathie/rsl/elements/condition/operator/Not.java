package fr.emmathie.rsl.elements.condition.operator;

import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class Not extends CondElement implements Condition{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Condition condition;
	
	public Not(Condition c) {
		this.condition = c;
	}

	@Override
	public boolean isTrue() {
		return !condition.isTrue();
	}

}
