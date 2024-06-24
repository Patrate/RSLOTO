package fr.emmathie.rsl.elements.action;

import com.sun.jna.platform.win32.WinDef.HWND;

import fr.emmathie.rsl.Main;
import fr.emmathie.rsl.Main.PRINT_LEVEL;
import fr.emmathie.rsl.Win32Utils;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class Focus extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<String> name;

	public Focus(Variable<String> wname) {
		this.name = wname;
	}

	@Override
	public boolean execute() {
		HWND hwnd = Win32Utils.getWindowHandle(this.name.getValue());
		if (hwnd == null) {
			Main.print("window handle null", PRINT_LEVEL.TRACE);
			return false;
		}

		int maxIt = 3;
		while (maxIt-- > 0) {
			if (Win32Utils.toForeground(hwnd))
				return true;
			Main.print("Couldnt focus", PRINT_LEVEL.TRACE);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Main.print("Couldnt focus three time", PRINT_LEVEL.TRACE);
		return false;
	}

}
