package fr.emmathie.rsl.elements.ocr;

import java.awt.AWTException;
import java.io.IOException;

import org.opencv.core.Mat;

import fr.emmathie.rsl.Win32Utils;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class TakeScreenshot extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<Mat> var;
	private Variable<String> wname;

	public TakeScreenshot(Variable<String> wname, Variable<Mat> var) {
		this.var = var;
		this.wname = wname;
	}

	@Override
	public boolean execute() {
		try {
			this.var.setValue(Win32Utils.screenShot(Win32Utils.getWindowHandle(wname.getValue())));
			return true;
		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
