package fr.emmathie.rsl.elements.action;

import fr.emmathie.rsl.Main;
import fr.emmathie.rsl.Main.PRINT_LEVEL;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.VariableStringBuilder;

public class Alert extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private VariableStringBuilder msg;

	public Alert(VariableStringBuilder msg) {
		this.msg = msg;
	}

	@Override
	public boolean execute() {
		Main.print(this.msg.toString(), PRINT_LEVEL.ALERT);
		return true;
	}

}
