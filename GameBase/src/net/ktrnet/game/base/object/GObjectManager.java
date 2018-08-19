package net.ktrnet.game.base.object;

import java.awt.Color;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// TODO
public class GObjectManager {

	private Image backImage = null;
	private Color backColor = null;

	private List<GObject> gObjects = null;
	private Map<Integer, List<Integer>> layeredList = null;

	public GObjectManager() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public List<GObject> getGameObjects() {
		return gObjects;
	}

	public List<GObject> getGameObjects(int layer) {
		return this.layeredList.get(layer).stream()
				.map(idx -> gObjects.get(idx))
				.collect(Collectors.toList());
	}

	public Set<Integer> getLayeres() {
		return this.layeredList.keySet();
	}

	public Image getBackImage() {
		return backImage;
	}

	public void setBackImage(Image backImage) {
		this.backImage = backImage;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

}
