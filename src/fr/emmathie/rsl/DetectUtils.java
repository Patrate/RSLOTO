package fr.emmathie.rsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class DetectUtils {
	private static int match_method;

	private static int border = 0;

	public static Mat loadImage(String path) {
		return Imgcodecs.imread(path, Imgcodecs.IMREAD_UNCHANGED);
	}

	public static List<Point> matchingMethod(Mat img, Mat templ) {
		return matchingMethod(img, templ, Imgproc.TM_SQDIFF_NORMED);// Imgproc.TM_CCOEFF);
	}

	public static List<Point> matchingMethod(Mat img, Mat templ, int matchMethod) {
		return pyMatchingMethod(img, templ, matchMethod, .99, 100);
	}

	/*
	 * public static void main(String[] args) { nu.pattern.OpenCV.loadLocally();
	 * loadImage("image/notAllMax.png"); loadImage("image/victoryLogoStars.png");
	 * pyMatchingMethod(loadImage("image/notAllMax.png"),
	 * loadImage("image/victoryLogoStars.png"), Imgproc.TM_SQDIFF_NORMED, 0.85, 100,
	 * false); }
	 */

	public static List<Point> pyMatchingMethod(Mat imgSrc, List<Mat> tmplSrc, int matchMethod, double threshold,
			int maxResult, boolean additive) {
		List<Point> ret = new ArrayList<Point>();
		for (Mat tmpl : tmplSrc) {
			ret.addAll(pyMatchingMethod(imgSrc, tmpl, matchMethod, threshold, maxResult));
			if (!additive && !ret.isEmpty()) {
				break;
			}
		}
		return ret;
	}

	public static List<Point> pyMatchingMethod(Mat imgSrc, Mat tmplSrc, int matchMethod, double threshold,
			int maxResult) {
		try {
			File imgFile = File.createTempFile("img", ".png");
			File tmplFile = File.createTempFile("templ", ".png");
			imgFile.deleteOnExit();
			tmplFile.deleteOnExit();
			Imgcodecs.imwrite(imgFile.getAbsolutePath(), imgSrc);
			Imgcodecs.imwrite(tmplFile.getAbsolutePath(), tmplSrc);
			String matchMethodString = "";
			switch (matchMethod) {
			case Imgproc.TM_SQDIFF_NORMED:
			default:
				matchMethodString = "cv2.TM_SQDIFF_NORMED";
			}
		
			try {
				ProcessBuilder processBuilder = new ProcessBuilder("python", "./matching.py", matchMethodString,
						imgFile.getAbsolutePath(), tmplFile.getAbsolutePath(), "" + threshold);
				processBuilder.redirectErrorStream(true);
				Process process;
				process = processBuilder.start();
				String result = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().parallel()
						.collect(Collectors.joining("\n"));
				int exitCode = process.waitFor();
				if (result.isEmpty() || result.isBlank() || result.length() < 5) {
					return new ArrayList<Point>();
				}
				List<Integer[]> pts = decrypt(result);
				List<Point> ret = new ArrayList<Point>();
				for (Integer[] ptsVal : pts) {
					ret.add(new Point(ptsVal[0], ptsVal[1]));
				}
				if (exitCode == 0) {
					// TODO manage exit errors
					return ret;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static List<Integer[]> decrypt(String s) {
		s = s.substring(1, s.length() - 1);
		String[] spl = s.split("\\], \\[", -1);
		List<Integer[]> ret = new ArrayList<Integer[]>();
		for (String subs : spl) {
			subs = subs.replace("[", "");
			subs = subs.replace("]", "");
			String[] vars = subs.split(", ", 2);
			ret.add(new Integer[] { Integer.valueOf(vars[0]), Integer.valueOf(vars[1]) });
		}
		return ret;
	}

	@Deprecated
	public static List<Point> matchingMethod(Mat imgSrc, Mat tmplSrc, int matchMethod, double threshold, int maxResult,
			boolean showImage) {
		Mat tmplGray = new Mat();
		Mat img = new Mat();
		Imgproc.cvtColor(imgSrc, img, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(tmplSrc, tmplGray, Imgproc.COLOR_BGR2GRAY);
		// Imgcodecs.imwrite("image/gray.png", tmplGray);

		Mat mask = new Mat();

		Imgproc.threshold(tmplGray, mask, 254, 255, Imgproc.THRESH_BINARY);

		Imgcodecs.imwrite("image/mask.png", mask);

		if (img.empty() || tmplGray.empty() || (mask.empty())) {
			System.out.println("Can't read one of the images");
			System.exit(-1);
		}

		Mat result = new Mat();

		Mat img_display = new Mat();
		img.copyTo(img_display);

		int result_cols = img.cols() - tmplGray.cols() + 1;
		int result_rows = img.rows() - tmplGray.rows() + 1;

		result.create(result_rows, result_cols, CvType.CV_32FC1);

		Boolean method_accepts_mask = (matchMethod == Imgproc.TM_SQDIFF || matchMethod == Imgproc.TM_CCORR_NORMED);
		if (method_accepts_mask) {
			Imgproc.matchTemplate(img, tmplGray, result, matchMethod, mask);
		} else {
			Imgproc.matchTemplate(img, tmplGray, result, matchMethod);
		}

		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

		Core.MinMaxLocResult mmr;
		double curVal = 1.;
		int maxIt = maxResult;

		List<Point> pointsRet = new ArrayList<Point>();

		Mat resultClone = result.clone();

		Scalar sc = (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED)
				? new Scalar(255, 255, 255)
				: new Scalar(0, 0, 0);

		while (curVal >= threshold && maxIt-- > 0) {
			Point matchLoc;

			mmr = Core.minMaxLoc(result);

			if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
				matchLoc = mmr.minLoc;
				curVal = 1. - mmr.minVal;
			} else {
				matchLoc = mmr.maxLoc;
				curVal = mmr.maxVal;
			}
			if (curVal < threshold) {
				break;
			}

			pointsRet.add(matchLoc);

			Imgproc.rectangle(img_display, matchLoc,
					new Point(matchLoc.x + tmplGray.cols(), matchLoc.y + tmplGray.rows()), new Scalar(0, 0, 0), 2, 1,
					0);

			Imgproc.rectangle(result, new Point(matchLoc.x - border, matchLoc.y - border),
					new Point(matchLoc.x + tmplGray.cols() + border, matchLoc.y + tmplGray.rows() + border), sc,
					Imgproc.FILLED, Imgproc.LINE_8, 0);

			Imgproc.rectangle(resultClone, new Point(matchLoc.x, matchLoc.y),
					new Point(matchLoc.x + tmplGray.cols(), matchLoc.y + tmplGray.rows()), new Scalar(0, 0, 0), 1, 8,
					0);
		}

		if (showImage || true) {
			HighGui.imshow("Image", img);
			HighGui.imshow("Template", tmplGray);
			HighGui.imshow("Mask", mask);
			HighGui.waitKey();
		}

		return pointsRet;
	}
}
