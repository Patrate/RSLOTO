package fr.emmathie.rsl;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.win32.W32APIOptions;


public interface User32 extends Library {
    User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

    HWND FindWindow(String lpClassName, String lpWindowName);
    boolean SetForegroundWindow(HWND hWnd);
    boolean SetWindowPos(HWND hWnd, HWND hWndInsertAfter, int X, int Y, int cx, int cy, int uFlags);
    HWND GetForegroundWindow();
    int GetWindowText(HWND hWnd, char[] lpString, int nMaxCount);
    boolean GetWindowRect(HWND Wnd, RECT rect);
}

interface WinDef {
    HWND HWND_TOP = new HWND(Pointer.createConstant(0));
    int SWP_NOSIZE = 0x0001;
    int SWP_NOMOVE = 0x0002;
    int SWP_SHOWWINDOW = 0x0040;
}