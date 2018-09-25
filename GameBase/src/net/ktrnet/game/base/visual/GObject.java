package net.ktrnet.game.base.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import net.ktrnet.game.base.logic.GUpdate;

// TODO
public class GObject implements GDraw, GUpdate {

	private String id = null;
	private String name = null;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
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

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
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

		if (this.image != null) {
			g2d.drawImage(this.image,
					this.x, this.y, this.width, this.height, Color.BLACK, null);
		} else {
			Color preColor = g2d.getColor();
			if (this.color != null) {
				g2d.setColor(this.color);
			} else {
				g2d.setColor(Color.BLACK);
			}
			g2d.fillRect(this.x, this.y, this.width, this.height);
			g2d.setColor(preColor);
		}

		return;
	}

	@Override
	public void update() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
