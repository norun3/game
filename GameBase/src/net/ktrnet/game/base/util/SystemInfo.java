package net.ktrnet.game.base.util;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class SystemInfo {

	private static SystemInfo instance = null;

	public static double getFrameWidth() {
		return instance.getWindowSize().getWidth();
	}

	public static double getFrameHeight() {
		return instance.getWindowSize().getHeight();
	}

	public static double getFramePosX() {
		return instance.getWindowPos().getX();
	}

	public static double getFramePoxY() {
		return instance.getWindowPos().getY();
	}

	public static SystemInfo getInstance() {
		if (instance == null) {
			instance = new SystemInfo();
		}
		return instance;
	}

	private JFrame frame = null;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	private Point getWindowPos() {

		return this.frame.getLocation();
	}

	private Dimension getWindowSize() {
		return this.frame.getSize();
	}

	private Dimension getCanvasSize() {
		return this.frame.getContentPane().getSize();
	}


}
