package net.ktrnet.game.base.object;

import java.awt.Color;
import java.awt.Image;

import net.ktrnet.game.base.util.SystemInfo;

public class GBackGround extends GObject {

	public GBackGround() {
		this(Color.BLACK);
	}

	public GBackGround(Image image) {
		super(0, 0,
				(int)SystemInfo.getInstance().getWindowSize().getWidth(),
				(int)SystemInfo.getInstance().getWindowSize().getHeight(),
				image);
	}

	public GBackGround(Color color) {
		super(0, 0,
			(int)SystemInfo.getInstance().getWindowSize().getWidth(),
			(int)SystemInfo.getInstance().getWindowSize().getHeight(),
			color);
	}

}
