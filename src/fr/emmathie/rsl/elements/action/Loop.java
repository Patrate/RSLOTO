package fr.emmathie.rsl.elements.action;

import fr.emmathie.rsl.Main;
import fr.emmathie.rsl.Main.PRINT_LEVEL;
import fr.emmathie.rsl.elements.Jump;
import fr.emmathie.rsl.elements.Variable;
import fr.emmathie.rsl.elements.condition.CondElement;
import fr.emmathie.rsl.elements.condition.Condition;

public class Loop extends CondElement implements Condition, Jump {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7387483896653050529L;
	
	private Condition condition;
	private Variable<Integer> jumpId;

	public Loop(Condition c, Variable<Integer> jumpId) {
		this.condition = c;
		this.jumpId = jumpId;
	}

	@Override
	public boolean isTrue() {
		if (!condition.isTrue()) {
			Main.print("Quitting", PRINT_LEVEL.DEBUG);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getJumpId() {
		return this.jumpId.getValue();
	}

}
