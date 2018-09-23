package net.ktrnet.game.base.util;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class SystemInfo {

	private static SystemInfo instance = null;

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

	private SystemInfo() {
	}

	public Point getWindowPos() {

		return this.frame.getLocation();
	}

	public Dimension getWindowSize() {
		return this.frame.getSize();
	}

	public Dimension getCanvasSize() {
		return this.frame.getContentPane().getSize();
	}


}
