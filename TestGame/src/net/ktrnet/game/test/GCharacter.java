package net.ktrnet.game.test;


import java.awt.Image;

import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.util.GameTime;

public class GCharacter extends GObject {

	/** ジャンプ速度[px/ms]  */
	private static final double JUMP_SPEED = 1;

	private long preActionTime = -1;

	private boolean jumping = false;
	private int height = 0;

	public GCharacter() {
		super();
	}

	public GCharacter(int x, int y, double scale, Image image) {
		super(x, y, scale, image);
	}

	public GCharacter(int x, int y, Image image) {
		super(x, y, image);
	}

	public GCharacter(int x, int y, int width, int height, Image image) {
		super(x, y, width, height, image);
	}

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

	public void update() {

		long time = GameTime.getSystemTime();

		if (this.jumping) {

			// ここからジャンプによる上移動
			long elapseTime = time - this.preActionTime;

			int y = this.getY();
			double jumpHeight = JUMP_SPEED * elapseTime;
			y = y - (int)jumpHeight;
			this.setY(this.getY() - (int)jumpHeight);

			this.height += jumpHeight;

			// ここからジャンプ解除処理
			if (height <= 0) {
				this.jumping = false;
				this.height = 0;
			}
		}

		this.preActionTime = time;
	}
}
