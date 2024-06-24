package fr.emmathie.rsl.elements.action;

import com.sun.jna.platform.win32.WinDef.HWND;

import fr.emmathie.rsl.Win32Utils;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class Resize extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<String> name;
	private Variable<Number> width, height;

	public Resize(Variable<String> name, Variable<Number> width, Variable<Number> height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean execute() {
		HWND hwnd = Win32Utils.getWindowHandle(this.name.getValue());
		if (hwnd == null)
			return false;
		return Win32Utils.resizeWindow(hwnd, 0, 0, (int) this.width.getValue(), (int) this.height.getValue());
	}

}
