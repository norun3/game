package net.ktrnet.game.action.object;


import java.awt.Image;

import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.util.GameTime;

public class GActionChara extends GObject {

	/** ジャンプ速度[px/ms]  */
	private static final double JUMP_SPEED = 1;

	/** オブジェクトの移動加速 */
	private double velocity = 0;

	private long preActionTime = -1;

	private boolean jumping = false;

	private boolean fixed = false;

	public GActionChara() {
		super();
	}

	public GActionChara(String id, int x, int y, double scale, Image image) {
		super(id, x, y, scale, image);
	}

	public GActionChara(String id, int x, int y, Image image) {
		super(id, x, y, image);
	}

	public GActionChara(String id, int x, int y, int width, int height, Image image) {
		super(id, x, y, width, height, image);
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

		// ジャンプ処理
		if (this.isJumping()) {

			// ここからジャンプによる上移動
			long elapseTime = time - this.preActionTime;

			// 等加速で上方向に移動（重力は環境ロジックでかける）
			double y = this.getY();
			double jumpHeight = JUMP_SPEED * elapseTime;
			y = y - jumpHeight;
			this.setY(this.getY() - jumpHeight);
		}

		this.preActionTime = time;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
