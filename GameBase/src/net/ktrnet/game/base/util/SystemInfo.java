package net.ktrnet.game.base.util;

import javax.swing.JFrame;

public class SystemInfo {

	private static JFrame frame = null;
	private static double fps = 60;
	private static double frameSec = 1 / fps;
	private static double frameMSec = frameSec * 1000;

	public static double getFrameWidth() {
		return frame.getWidth();
	}

	public static double getFrameHeight() {
		return frame.getHeight();
	}

	public static double getFramePosX() {
		return frame.getX();
	}

	public static double getFramePoxY() {
		return frame.getY();
	}

	public static double getCanvasWidth() {
		return frame.getContentPane().getWidth();
	}

	public static double getCanvasHeight() {
		return frame.getContentPane().getHeight();
	}

	public static double getFps() {
		return fps;
	}

	public static double getFrameSec() {
		return frameSec;
	}

	public static double getFrameMSec() {
		return frameMSec;
	}

	public static void setFrame(JFrame frm) {
		frame = frm;
	}


}
