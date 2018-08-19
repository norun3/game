package net.ktrnet.game.base.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyStateManager {

	private Map<Integer, GameKeyEvent> keyStateMap = null;

	public KeyStateManager() {
		this.keyStateMap = new HashMap<Integer, GameKeyEvent>();
	}

	public void setPressKey(int keyCode, long pressTime) {

		// まだキーイベントが消化されていない場合
		if (keyStateMap.containsKey(keyCode)) {

			GameKeyEvent gke = keyStateMap.get(keyCode);

			switch (gke.getKeyState()) {
			// 前回から押下状態のままの場合、何もしない
			case KeyEvent.KEY_PRESSED:
				// 処理なし
				break;
			// 前回が離されていた場合、押下状態にする
			case KeyEvent.KEY_RELEASED:
				gke.setKeyState(KeyEvent.KEY_PRESSED);
				gke.setKeyPressTime(pressTime);
				gke.setKeyReleaseTime(0);
				break;
			}

		// キーイベント消化or最初のキー押下の場合
		} else {
			// 新しく押下状態で登録する
			GameKeyEvent gke = new GameKeyEvent(keyCode, KeyEvent.KEY_PRESSED);
			gke.setKeyPressTime(pressTime);
			gke.setKeyReleaseTime(0);
			keyStateMap.put(keyCode, gke);
		}

		return;
	}

	public void setReleaseKey(int keyCode, long releaseTime) {

		// まだキーイベントが消化されていない場合
		if (keyStateMap.containsKey(keyCode)) {

			GameKeyEvent gke = keyStateMap.get(keyCode);

			switch (gke.getKeyState()) {
			// 前回が押下状態の場合、離れた状態にする
			case KeyEvent.KEY_PRESSED:
				gke.setKeyState(KeyEvent.KEY_RELEASED);
				gke.setKeyPressTime(0);
				gke.setKeyReleaseTime(releaseTime);
				break;
			// 前回から離されていた場合、なにもしない
			case KeyEvent.KEY_RELEASED:
				// 処理なし
				break;
			}

		// キーイベント消化or最初のキー離しの場合
		} else {
			// 新しく離した状態で登録する
			GameKeyEvent gke = new GameKeyEvent(keyCode, KeyEvent.KEY_RELEASED);
			gke.setKeyPressTime(0);
			gke.setKeyReleaseTime(releaseTime);
			keyStateMap.put(keyCode, gke);
		}

		return;
	}

	public boolean isPressed(int keyCode) {

		boolean bPressed = false;

		if (this.keyStateMap.containsKey(keyCode)) {

			GameKeyEvent e = this.keyStateMap.get(keyCode);
			if (e.getKeyState() == KeyEvent.KEY_PRESSED) {
				bPressed = true;
			} else {
				bPressed = false;
			}

		} else {
			bPressed = false;
		}

		return bPressed;
	}

	public boolean isReleased(int keyCode) {

		boolean bReleased = false;

		if (this.keyStateMap.containsKey(keyCode)) {

			GameKeyEvent e = this.keyStateMap.get(keyCode);
			if (e.getKeyState() == KeyEvent.KEY_RELEASED) {
				bReleased = true;
			} else {
				bReleased = false;
			}

		} else {
			bReleased = false;
		}

		return bReleased;
	}
}
