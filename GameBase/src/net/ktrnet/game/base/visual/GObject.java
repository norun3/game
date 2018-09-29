package net.ktrnet.game.base.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import net.ktrnet.game.base.logic.GUpdate;

// TODO
public class GObject implements GDraw, GUpdate {

	private String id = null;
	private String name = null;
	private double x = 0;
	private double y = 0;
	private double width = 0;
	private double height = 0;
	private Image image = null;
	private Color color = null;


	public GObject() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GObject(String id, int x, int y, Image image) {
		this.setOriginalImage(x, y, image);
	}

	public GObject(String id, int x, int y, int width, int height, Image image) {
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

	public GObject(String id, int x, int y, double scale, Image image) {

		this.setId(id);

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		width *= scale;
		height *= scale;

		this.setScaleImage(x, y, width, height, image);
	}

	public void setOriginalImage(int x, int y, Image image) {
		this.setScaleImage(x, y, image.getWidth(null), image.getHeight(null), image);
	}

	public void setScaleImage(int x, int y, int width, int height, Image image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = null;
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


	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (this.getImage() != null) {
			g2d.drawImage(this.getImage(),
					(int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), Color.BLACK, null);
		} else {
			Color preColor = g2d.getColor();
			if (this.getColor() != null) {
				g2d.setColor(this.getColor());
			} else {
				g2d.setColor(Color.BLACK);
			}
			g2d.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
			g2d.setColor(preColor);
		}

		return;
	}

	@Override
	public void update() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
