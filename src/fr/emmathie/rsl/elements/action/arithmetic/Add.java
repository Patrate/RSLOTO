package fr.emmathie.rsl.elements.action.arithmetic;

import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;


public class Add extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;
	
	private Variable<Number> var;
	private Number num;
	
	public Add(Variable<Number> var, Number num) {
		this.var = var;
		this.num = num;
	}
	
	@Override
	public boolean execute() {
		this.var.setValue(this.var.getValue().intValue() + this.num.intValue());
		return true;
	}

}
