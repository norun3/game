package net.ktrnet.game.base.visual;

import java.awt.Color;
import java.awt.Image;

import net.ktrnet.game.base.util.SystemInfo;

public class GBackGround extends GObject {

	private final static String BACKGROUND_ID = "background";

	public GBackGround() {
		this(Color.BLACK);
	}

	public GBackGround(Image image) {
		super(BACKGROUND_ID, 0, 0,
				(int)SystemInfo.getInstance().getWindowSize().getWidth(),
				(int)SystemInfo.getInstance().getWindowSize().getHeight(),
				image);
	}

	public GBackGround(Color color) {
		super(BACKGROUND_ID, 0, 0,
			(int)SystemInfo.getInstance().getWindowSize().getWidth(),
			(int)SystemInfo.getInstance().getWindowSize().getHeight(),
			color);
	}

}
