package fr.emmathie.rsl.elements.action;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class ShowImage extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<Mat> image;

	public ShowImage(Variable<Mat> image) {
		this.image = image;
	}

	@Override
	public boolean execute() {
		HighGui.imshow("Image", image.getValue());
		HighGui.waitKey();
		return true;
	}

}
