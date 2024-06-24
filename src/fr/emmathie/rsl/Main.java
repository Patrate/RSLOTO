package fr.emmathie.rsl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Mat;

import fr.emmathie.rsl.elements.Variable;
import fr.emmathie.rsl.elements.VariableStringBuilder;
import fr.emmathie.rsl.elements.action.Alert;
import fr.emmathie.rsl.elements.action.Click;
import fr.emmathie.rsl.elements.action.Exit;
import fr.emmathie.rsl.elements.action.Focus;
import fr.emmathie.rsl.elements.action.Goto;
import fr.emmathie.rsl.elements.action.Loop;
import fr.emmathie.rsl.elements.action.Resize;
import fr.emmathie.rsl.elements.action.Wait;
import fr.emmathie.rsl.elements.action.arithmetic.Add;
import fr.emmathie.rsl.elements.condition.operator.If;
import fr.emmathie.rsl.elements.condition.operator.Not;
import fr.emmathie.rsl.elements.condition.operator.numeric.BiggerThan;
import fr.emmathie.rsl.elements.condition.operator.numeric.Equal;
import fr.emmathie.rsl.elements.ocr.GetImageCount;
import fr.emmathie.rsl.elements.ocr.TakeScreenshot;
import fr.emmathie.rsl.gui.MainFrame;

public class Main {

	private List<Script> scripts;
	private static final Variable<String> RSL = new Variable<String>("RSL", "Raid: Shadow Legends");
	private static final PRINT_LEVEL debugLevel = PRINT_LEVEL.TRACE;// PRINT_LEVEL.TRACE;
	private static final Main INSTANCE = new Main();

	public static Main getInstance() {
		return INSTANCE;
	}

	public static void main(String[] args) {
		INSTANCE.scripts.get(0).run();
	}

	private Main() {
		nu.pattern.OpenCV.loadLocally();
		scripts = new ArrayList<Script>();

		populateDummy();
	}

	public List<Script> getScripts() {
		return scripts;
	}

	public enum PRINT_LEVEL {
		TRACE(6), DEBUG(5), LOG(4), ALERT(3), WARNING(2), ERROR(1), FATAL(0);

		private int lvl;

		private PRINT_LEVEL(int i) {
			lvl = i;
		}
	}

	public static void print(Object msg) {
		print(msg, PRINT_LEVEL.LOG);
	}

	public static void print(Object msg, PRINT_LEVEL level) {
		if (level.lvl <= debugLevel.lvl) {
			String timestamp = ZonedDateTime.now(ZoneId.systemDefault())
					.format(DateTimeFormatter.ofPattern("[HH:mm:ss] "));
			String prefix = "";
			String sufix = "\u001B[0m"; // reset

			switch (level) {
			case FATAL:
			case ERROR:
			case DEBUG:
				prefix = "\u001B[31m"; // red
				break;
			case WARNING:
			case ALERT:
				prefix = "\u001B[33m"; // yellow
				break;
			case TRACE:
				prefix = "\u001B[36m"; // cyan
				break;
			case LOG:
			default:
				prefix = "";
			}
			if (MainFrame.getMainFrame() != null)
				MainFrame.getMainFrame().print(prefix + timestamp + msg + sufix);
			else
				System.out.println(prefix + timestamp + msg + sufix);
		}
	}

	private void populateDummy() {
		Script s1 = new Script("test");

		Variable<Mat> sch = new Variable<Mat>("RSL screenshot", null);
		Variable<Number> count = new Variable<Number>("count", 99);
		Variable<Number> countb = new Variable<Number>("countb", 0);
		Variable<Number> it = new Variable<Number>("it", 0);

		Variable<Number> width = new Variable<Number>("width", 1280);
		Variable<Number> height = new Variable<Number>("height", 960);

		VariableStringBuilder sbVar3 = new VariableStringBuilder("sbc", new StringBuilder("Count = _countb_"), countb);

		Variable<List<Mat>> victoryTmpl = new Variable<List<Mat>>("templ", Arrays.asList(
				DetectUtils.loadImage("image/victoryLogo.png"), DetectUtils.loadImage("image/victoryLogoStars.png")));
		Variable<List<Mat>> maxLvlTmpl = new Variable<List<Mat>>("templ",
				Arrays.asList(DetectUtils.loadImage("image/lvlMax.png")));

		s1.addElement(new Focus(RSL));
		s1.addElement(new Resize(RSL, width, height));
		s1.addElement(new TakeScreenshot(RSL, sch));
		s1.addElement(new GetImageCount(victoryTmpl, sch, countb));

		s1.addElement(new If(new Equal(countb, new Variable<Number>("one", 1)), new Variable<Integer>("", 7),
				new Variable<Integer>("", 5)));
		s1.addElement(new Wait(40000));
		s1.addElement(new Goto(new Variable<Integer>("", 0)));

		s1.addElement(new GetImageCount(maxLvlTmpl, sch, count));
		s1.addElement(new If(new Not(new BiggerThan(count, new Variable<Number>("one", 1))),
				new Variable<Integer>("", 9), new Variable<Integer>("", 13)));
		s1.addElement(new Click(680, 900));
		s1.addElement(new Wait(1500));
		s1.addElement(new Add(it, 1));
		s1.addElement(new Loop(new BiggerThan(new Variable<Number>("maxIt", 100), it), new Variable<Integer>("", 0)));
		s1.addElement(new Exit());

		Script s2 = new Script("test2");

		s2.addElement(new Focus(RSL));
		s2.addElement(new Focus(RSL));
		s2.addElement(new Resize(RSL, width, height));
		s2.addElement(new TakeScreenshot(RSL, sch));

		Script s3 = new Script("test3");
		s3.addElement(new GetImageCount(
				new Variable<List<Mat>>("b",
						Arrays.asList(DetectUtils.loadImage("image/victoryLogo.png"),
								DetectUtils.loadImage("image/victoryLogoStars.png"))),
				new Variable<Mat>("a", DetectUtils.loadImage("image/notAllMax.png")), countb)); // 1
		s3.addElement(new Alert(sbVar3));

		s3.addElement(new GetImageCount(
				new Variable<List<Mat>>("b",
						Arrays.asList(DetectUtils.loadImage("image/victoryLogo.png"),
								DetectUtils.loadImage("image/victoryLogoStars.png"))),
				new Variable<Mat>("a", DetectUtils.loadImage("image/inBattle.png")), countb)); // 0
		s3.addElement(new Alert(sbVar3));

		s3.addElement(new GetImageCount(
				new Variable<List<Mat>>("b", Arrays.asList(DetectUtils.loadImage("image/lvlMax.png"))),
				new Variable<Mat>("a", DetectUtils.loadImage("image/notAllMax.png")), countb)); // 1
		s3.addElement(new Alert(sbVar3));

		s3.addElement(new GetImageCount(
				new Variable<List<Mat>>("b", Arrays.asList(DetectUtils.loadImage("image/lvlMax.png"))),
				new Variable<Mat>("a", DetectUtils.loadImage("image/4on5Max.png")), countb)); // 4
		s3.addElement(new Alert(sbVar3));

		s3.addElement(new GetImageCount(
				new Variable<List<Mat>>("b", Arrays.asList(DetectUtils.loadImage("image/lvlMax.png"))),
				new Variable<Mat>("a", DetectUtils.loadImage("image/inBattle.png")), countb)); // 0
		s3.addElement(new Alert(sbVar3));

		scripts.add(s1);
		scripts.add(s2);
		scripts.add(s3);
	}
}
