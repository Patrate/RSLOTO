package fr.emmathie.rsl.elements.condition.operator;

import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class Or extends CondElement implements Condition{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Condition c1, c2;
	
	public Or(Condition a, Condition b) {
		this.c1 = a;
		this.c2 = b;
	}

	@Override
	public boolean isTrue() {
		return c1.isTrue() || c2.isTrue();
	}

}
