package fr.emmathie.rsl.elements.ocr;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import fr.emmathie.rsl.DetectUtils;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Variable;

public class GetImageCount extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424823339323966808L;

	private Variable<Number> var;
	private Variable<Mat> matVar;
	private Variable<List<Mat>> templ;

	public GetImageCount(Variable<List<Mat>> templ, Variable<Mat> matVar, Variable<Number> var) {
		this.var = var;
		this.matVar = matVar;
		this.templ = templ;
	}

	@Override
	public boolean execute() {
		List<Point> pList;
		pList = DetectUtils.pyMatchingMethod(this.matVar.getValue(), templ.getValue(),
				Imgproc.TM_CCOEFF,
				.98, 100, true);
		this.var.setValue(pList.size());
		return true;
	}
}
