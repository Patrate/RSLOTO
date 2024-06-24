package fr.emmathie.rsl.elements.getter;

import com.sun.jna.platform.win32.WinDef.HWND;

import fr.emmathie.rsl.Main;
import fr.emmathie.rsl.Win32Utils;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class GetForegroundWindow extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<String> var;

	public GetForegroundWindow(Variable<String> var) {
		this.var = var;
	}

	@Override
	public boolean execute() {
		HWND hwnd = Win32Utils.getForegroundWindow();
		if (hwnd == null)
			return false;
		String wname = Win32Utils.getHWNDName(hwnd);
		if (wname.isEmpty())
			return false;
		var.setValue(wname);
		Main.print(wname);
		return true;
	}

}
