package net.ktrnet.game.base.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import net.ktrnet.game.base.logic.GUpdate;
import net.ktrnet.game.base.util.CollegionUtil;
import net.ktrnet.game.base.visual.GDraw;

// TODO
public class GObject implements GDraw, GUpdate {

	private String id = null;
	private String name = null;
	private double x = 0;
	private double y = 0;
	private double width = 0;
	private double height = 0;
	private BufferedImage image = null;
	private Color color = null;
	private int repeatw = 1;
	private int repeath = 1;

	private Shape shape = null;

	public GObject() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GObject(String id, int x, int y, BufferedImage image) {
		this.setOriginalImage(x, y, image);
	}

	public GObject(String id, int x, int y, int width, int height, BufferedImage image) {
		this.setScaleImage(x, y, width, height, image);
	}

	public GObject(String id, int x, int y, int width, int height, Color color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = null;
		this.color = color;
	}

	public GObject(String id, int x, int y, double scale, BufferedImage image) {

		this.setId(id);

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		width *= scale;
		height *= scale;

		this.setScaleImage(x, y, width, height, image);
	}

	public void setOriginalImage(int x, int y, BufferedImage image) {
		this.setScaleImage(x, y, image.getWidth(null), image.getHeight(null), image);
	}

	public void setScaleImage(int x, int y, int width, int height, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = null;
		this.shape = new Rectangle(0, 0, width, height);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public BufferedImage getImage() {
		return image;
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getRepeatw() {
		return repeatw;
	}

	public void setRepeatw(int repeatw) {
		this.repeatw = repeatw;
	}

	public int getRepeath() {
		return repeath;
	}

	public void setRepeath(int repeath) {
		this.repeath = repeath;
	}

	public double getRepeatWidth() {
		return this.getRepeatw() * this.getWidth();
	}

	public double getRepeatHeight() {
		return this.getRepeath() * this.getHeight();
	}

	public boolean hit(GObject gobj) {
		return CollegionUtil.hit(this.shape, gobj.shape);
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (this.getImage() != null) {
			int drawx = 0;
			int drawy = 0;
			int drawWidth = (int)this.getWidth();
			int drawHeight = (int)this.getHeight();

			for (int w = 0 ; w < this.getRepeatw() ; w++) {

				for (int h = 0 ; h < this.getRepeath() ; h++) {

					drawx = (int)this.getX() + (w * drawWidth);
					drawy = (int)this.getY() + (h * drawHeight);

					g2d.drawImage(this.getImage(),
							drawx, drawy, drawWidth, drawHeight, Color.BLACK, null);
				}
			}

		} else {
			Color preColor = g2d.getColor();
			if (this.getColor() != null) {
				g2d.setColor(this.getColor());
			} else {
				g2d.setColor(Color.BLACK);
			}

			int drawx = (int)this.getX();
			int drawy = (int)this.getY();
			int drawWidth = (int)this.getWidth();
			int drawHeight = (int)this.getHeight();

			for (int w = 0 ; w < this.getRepeatw() ; w++) {

				for (int h = 0 ; h < this.getRepeath() ; h++) {

					drawx = w * (int)this.getWidth();
					drawy = h * (int)this.getHeight();

					g2d.fillRect(drawx, drawy, drawWidth, drawHeight);
				}
			}


			g2d.setColor(preColor);
		}

		return;
	}

	@Override
	public void update() {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void updateClipRegion() {

		if (this.image != null) {
			System.out.println("call updateClipRegion");
			this.image.getGraphics().setClip(
					0, 0, (int)this.getWidth(), (int)this.getHeight());
		}
	}


}
