package fr.emmathie.rsl.elements.action;

import fr.emmathie.rsl.elements.Element;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Click extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private int x, y;
	
	public Click(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean execute() {
		
		try {
			Robot bot = new Robot();
		    bot.mouseMove(x, y);
		    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			return true;
		} catch (AWTException e) {
			e.printStackTrace();
			return false;
		}
	}

}
