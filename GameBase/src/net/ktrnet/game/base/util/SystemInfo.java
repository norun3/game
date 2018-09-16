package net.ktrnet.game.base.util;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class SystemInfo {

	private static SystemInfo instance = null;

	public static SystemInfo getInstance() {
		if (instance == null) {
			// instance = new SystemUtil();
		}
		return instance;
	}

	private JFrame frame = null;

	private SystemInfo(JFrame frame) {
		this.frame = frame;
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
