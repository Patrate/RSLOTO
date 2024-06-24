package fr.emmathie.rsl.elements.action;

import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.condition.Condition;

public class WaitUntil extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private int duration, timeout;
	private Condition condition;

	public WaitUntil(Condition c, int duration, int timeout) {
		this.duration = duration;
		this.condition = c;
		this.timeout = timeout;
	}

	@Override
	public boolean execute() {
		try {
			int it = 0, maxIt = timeout / duration;
			boolean isok = false;
			while (!(isok = condition.isTrue()) && it < maxIt) {
				Thread.sleep(duration);
				it += 1;
			}
			return isok;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
