package fr.emmathie.rsl.elements.condition.operator;

import fr.emmathie.rsl.elements.Jump;
import fr.emmathie.rsl.elements.Variable;
import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class If extends CondElement implements Condition, Jump {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Condition condition;
	private Variable<Integer> ifTrue, ifFalse;
	private int jumpId;

	public If(Condition c, Variable<Integer> ifTrue, Variable<Integer> ifFalse) {
		this.condition = c;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
		this.jumpId = -1;
	}

	@Override
	public boolean isTrue() {
		if (condition.isTrue()) {
			jumpId = ifTrue.getValue();
		} else {
			jumpId = ifFalse.getValue();
		}
		return true;
	}

	@Override
	public int getJumpId() {
		return jumpId;
	}

}
