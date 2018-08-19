package net.ktrnet.game.base.object;

import java.awt.Image;

// TODO
public class GObject {

	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private Image image = null;


	public GObject() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GObject(int x, int y, Image image) {
		this.setOriginalImage(x, y, image);
	}

	public GObject(int x, int y, int width, int height, Image image) {
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

}
