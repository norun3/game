package net.ktrnet.game.test;


import net.ktrnet.game.base.object.GObject;

public class GCharacter extends GObject {

	private long preActionTime = -1;

	private boolean jumping = false;
	private int height = 0;

	// pixel / ms
	private int acceleration = 0;

	public void walk() {
		this.setX(this.getX() + 10);
	}

	public void back() {
		this.setX(this.getX() - 10);
	}

	public void jump() {
		// 初速をセット
		// ステータスをセット
		this.jumping = true;


	}

	public void update(long time) {

		if (this.jumping) {

			int y = this.getY();
			int jumpHeight = acceleration * (int)(time - preActionTime);
			y = y - jumpHeight;
			this.setY(y);

			if (height <= 0) {
				this.jumping = false;
				this.height = 0;
			}
		}
	}
}
