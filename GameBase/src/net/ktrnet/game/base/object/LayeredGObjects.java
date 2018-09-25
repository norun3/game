package net.ktrnet.game.base.object;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.ktrnet.game.base.visual.GObject;

public class LayeredGObjects {

	private List<GObject> gObjects = null;
	private Map<Integer, List<Integer>> layeredList = null;

	public LayeredGObjects() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public List<GObject> getLayerList(int layer) {

		return layeredList.get(layer).stream()
					.map(idx -> gObjects.get(idx))
					.collect(Collectors.toList());
	}

	public List<GObject> getAllList() {

		return gObjects;
	}

}
