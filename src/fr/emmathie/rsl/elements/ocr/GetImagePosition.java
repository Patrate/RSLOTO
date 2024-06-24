package fr.emmathie.rsl.elements.ocr;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import fr.emmathie.rsl.DetectUtils;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class GetImagePosition extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<Point> var;
	private Variable<Mat> matVar;
	private Variable<List<Mat>> templ;

	public GetImagePosition(Variable<List<Mat>> templ, Variable<Mat> matVar, Variable<Point> var) {
		this.var = var;
		this.matVar = matVar;
		this.templ = templ;
	}

	@Override
	public boolean execute() {
		List<Point> pList = DetectUtils.pyMatchingMethod(this.matVar.getValue(), templ.getValue(), Imgproc.TM_CCOEFF,
				.98, 100, false);
		if (pList.isEmpty())
			return false;
		this.var.setValue(pList.get(0));
		return true;

	}

}
