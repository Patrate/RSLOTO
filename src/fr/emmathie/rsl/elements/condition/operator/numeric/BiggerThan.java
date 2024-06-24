package fr.emmathie.rsl.elements.condition.operator.numeric;

import fr.emmathie.rsl.elements.Variable;
import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class BiggerThan extends CondElement implements Condition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<Number> a, b;

	public BiggerThan(Variable<Number> a, Variable<Number> b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean isTrue() {
		return a.getValue().doubleValue() > b.getValue().doubleValue();
	}

}
