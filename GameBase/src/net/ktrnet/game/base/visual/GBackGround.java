package net.ktrnet.game.base.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import net.ktrnet.game.base.util.SystemInfo;

public class GBackGround extends GObject {

	private final static String BACKGROUND_ID = "background";

	public GBackGround() {
		this(Color.BLACK);
	}

	public GBackGround(Image image) {
		super(BACKGROUND_ID, 0, 0, 0, 0, image);
	}

	public GBackGround(Color color) {
		super(BACKGROUND_ID, 0, 0, 0, 0, color);
	}

	@Override
	public double getWidth() {
		return SystemInfo.getInstance().getWindowSize().getWidth();
	}

	@Override
	public double getHeight() {
		return SystemInfo.getInstance().getWindowSize().getHeight();
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}




}
