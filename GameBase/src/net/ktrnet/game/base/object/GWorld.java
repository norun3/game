package net.ktrnet.game.base.object;

import java.awt.Graphics2D;

public class GWorld implements GDraw, GUpdate {

	private LayerList<GObject> objects = null;

	public GWorld() {
		this.objects = new LayerList<GObject>();
	}

	public int countLayer() {
		return objects.size();
	}

	@Override
	public void draw(Graphics2D g2d) {
		this.objects.forEach((layer, list) -> list.forEach(e -> e.draw(g2d)));
	}

	@Override
	public void update() {
		this.objects.forEach((layer, list) -> list.forEach(e -> e.update()));
	}

	public void addObject(GObject gobj) {
		this.addObject(0, gobj);
	}

	public void addObject(int layer, GObject gobj) {
		objects.add(layer, gobj);
	}

	public void removeObject(GObject gobj) {
		this.removeObject(0, gobj);
	}

	public void removeObject(int layer, GObject gobj) {
		objects.remove(layer, gobj);
	}

	public void removeObject(int index) {
		this.removeObject(0, index);
	}

	public void removeObject(int layer, int index) {
		objects.remove(layer, index);
	}

}
