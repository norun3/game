package net.ktrnet.game.base.input;

// TODO
public class GameKeyEvent {

	private int keyCode = 0;

	private int keyState = 0;

	private long keyPressTime = 0;

	private long keyReleaseTime = 0;

	public GameKeyEvent() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GameKeyEvent(int keyCode, int keyState) {
		this.keyCode = keyCode;
		this.keyState = keyState;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getKeyState() {
		return keyState;
	}

	public void setKeyState(int keyState) {
		this.keyState = keyState;
	}

	public long getKeyPressTime() {
		return keyPressTime;
	}

	public void setKeyPressTime(long keyPressTime) {
		this.keyPressTime = keyPressTime;
	}

	public long getKeyReleaseTime() {
		return keyReleaseTime;
	}

	public void setKeyReleaseTime(long keyReleaseTime) {
		this.keyReleaseTime = keyReleaseTime;
	}



}
