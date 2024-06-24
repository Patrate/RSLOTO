package fr.emmathie.rsl;

import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.AWTException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import com.sun.jna.Native;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import com.sun.jna.platform.win32.WinDef.RECT;
import java.io.ByteArrayOutputStream;

public class Win32Utils {

	//getWindowHandle("Raid: Shadow Legends");
	
	public static HWND getWindowHandle(String windowTitle) {
        // Call FindWindow to get the window handle
        return User32.INSTANCE.FindWindow(null, windowTitle);
    }
	
	public static boolean resizeWindow(HWND handle, int x, int y, int width, int height) {
		return User32.INSTANCE.SetWindowPos(handle, WinDef.HWND_TOP, x, y, width, height, WinDef.SWP_SHOWWINDOW);
	}
	
	public static boolean toForeground(HWND hwnd) {
		return User32.INSTANCE.SetForegroundWindow(hwnd);
	}
	
	public static HWND getForegroundWindow() {
		return User32.INSTANCE.GetForegroundWindow();
	}
	
	public static String getHWNDName(HWND hwnd) {
		char[] windowText = new char[512];
	    User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
	    return Native.toString(windowText);
	}
	
	public static Mat screenShot() throws AWTException, IOException {
		return screenShot(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	}
	
	public static Mat screenShot(HWND hwnd) throws AWTException, IOException {
		RECT lpRect= new RECT();
		User32.INSTANCE.GetWindowRect(hwnd, lpRect);
		return screenShot(new Rectangle(lpRect.left + 8, lpRect.top + 32, Math.abs(lpRect.right - lpRect.left) - 16, Math.abs(lpRect.bottom - lpRect.top) - 40));
	}
	
	public static Mat screenShot(Rectangle capture) throws AWTException, IOException {

	    Robot r = new Robot();
	    BufferedImage Image = r.createScreenCapture(capture);
	    Mat mat = BufferedImage2Mat(Image);

	    return mat;

	}

	public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    ImageIO.write(image, "jpg", byteArrayOutputStream);
	    byteArrayOutputStream.flush();
	    return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
	}

}
