package fr.emmathie.rsl.elements.action;

import fr.emmathie.rsl.elements.Jump;
import fr.emmathie.rsl.elements.Variable;
import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class Goto extends CondElement implements Condition, Jump {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7387483896653050529L;

	private Variable<Integer> jumpId;

	public Goto(Variable<Integer> jumpId) {
		this.jumpId = jumpId;
	}

	@Override
	public boolean isTrue() {
		return true;
	}

	@Override
	public int getJumpId() {
		return this.jumpId.getValue();
	}

}
