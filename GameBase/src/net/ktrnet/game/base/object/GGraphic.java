package net.ktrnet.game.base.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public class GGraphic implements GDraw, GUpdate {

	private GBackGround background = null;

	private GWorld world = null;

	private GFrontGround frontground= null;

	public GGraphic() {
		this.background = new GBackGround();
		this.world = new GWorld();
		this.frontground = new GFrontGround();
	}

	public void setBackgroundColor(Color color) {
		this.background.setColor(color);
	}

	public void setBackgroundImage(Image image) {
		this.background.setImage(image);
	}

	public void addObject(GObject gobj) {
		this.world.addObject(gobj);
	}

	public void addObject(int layer, GObject gobj) {
		this.world.addObject(layer, gobj);
	}

	public void removeObject(GObject gobj) {
		this.world.removeObject(gobj);
	}

	public void removeObject(int layer, GObject gobj) {
		this.world.removeObject(layer, gobj);
	}

	public int countLayer() {
		return this.world.countLayer();
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (this.background != null) {
			this.background.draw(g2d);
		}

		if (this.world != null) {
			this.world.draw(g2d);
		}

		if (this.frontground != null) {
			this.frontground.draw(g2d);
		}

	}

	@Override
	public void update() {

		if (this.background != null) {
			this.background.update();
		}

		if (this.world != null) {
			this.world.update();
		}

		if (this.frontground != null) {
			this.frontground.update();
		}

	}

	public GBackGround getBackground() {
		return background;
	}

	public void setBackground(GBackGround background) {
		this.background = background;
	}

	public GWorld getWorld() {
		return world;
	}

	public void setWorld(GWorld world) {
		this.world = world;
	}

	public GFrontGround getFrontground() {
		return frontground;
	}

	public void setFrontground(GFrontGround frontground) {
		this.frontground = frontground;
	}

}
