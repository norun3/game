package net.ktrnet.game.base.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourceUtil {

	private static Map<String, BufferedImage> loadImages = null;

	static {
		loadImages = new HashMap<String, BufferedImage>();
	}

	public static BufferedImage getImage(String path) throws IOException {

		// 既にロード済みならそれを返却
		if (loadImages.containsKey(path)) {
			return loadImages.get(path);
		}

		// まだロードしていなかったらロードして返却
		BufferedImage bufImg = null;
		URL url = ResourceUtil.class.getClassLoader().getResource(path);
		bufImg = ImageIO.read(url);

		loadImages.put(path, bufImg);

		return bufImg;
	}

}
