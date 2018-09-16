package net.ktrnet.game.base.object;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GFrontGround implements GDraw {

	private List<GObject> objects = null;

	public GFrontGround() {
		this.objects = new ArrayList<GObject>();
	}

	@Override
	public void draw(Graphics2D g2d) {
		objects.forEach(gobj -> draw(g2d));
	}


	public void addObject(GObject gobj) {
		objects.add(gobj);
	}

	public void removeObject(GObject gobj) {
		objects.remove(gobj);
	}

	public void removeObject(int index) {
		objects.remove(index);
	}
}
