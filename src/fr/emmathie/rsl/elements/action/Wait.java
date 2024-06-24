package fr.emmathie.rsl.elements.action;

import fr.emmathie.rsl.elements.Element;


public class Wait extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private int duration;
	
	public Wait(int duration) {
		this.duration = duration;
	}
	
	@Override
	public boolean execute() {
		try {
			Thread.sleep(duration);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

}
