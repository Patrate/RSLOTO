package fr.emmathie.rsl.elements.action;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import fr.emmathie.rsl.elements.Element;
public class AltTab extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	public AltTab() {
	}
	
	@Override
	public boolean execute() {
		try {
			Robot bot = new Robot();
			bot.keyPress(KeyEvent.VK_ALT);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_ALT);
		    return true;
		} catch (AWTException e) {
			e.printStackTrace();
			return false;
		}
	}

}
