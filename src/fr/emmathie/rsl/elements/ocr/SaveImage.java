package fr.emmathie.rsl.elements.ocr;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class SaveImage extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<Mat> var;
	private Variable<String> iname;

	public SaveImage(Variable<String> iname, Variable<Mat> var) {
		this.var = var;
		this.iname = iname;
	}

	@Override
	public boolean execute() {
		Imgcodecs.imwrite(iname.getValue(), this.var.getValue());
		return true;
	}

}
