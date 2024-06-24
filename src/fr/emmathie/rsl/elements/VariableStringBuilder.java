package fr.emmathie.rsl.elements;

public class VariableStringBuilder extends Variable<StringBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1517608335651931012L;
	public Variable<?>[] args;

	public VariableStringBuilder(String name, StringBuilder value, Variable<?> ... args) {
		super(name, value);
		this.args = args;
	}

	@Override
	public String toString() {
		String stringTmp = this.getValue().toString();
		for (Variable<?> var : args) {
			stringTmp = stringTmp.replaceAll("_" + var.getName() + "_", var.getValue().toString());
		}
		return stringTmp;
	}
}
