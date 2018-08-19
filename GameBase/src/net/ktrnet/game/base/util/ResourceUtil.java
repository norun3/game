package net.ktrnet.game.base.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ResourceUtil {

	public static BufferedImage loadImage(String path) throws IOException {

		BufferedImage bufImg = null;
		URL url = ResourceUtil.class.getClassLoader().getResource(path);
		bufImg = ImageIO.read(url);

		return bufImg;
	}
}
