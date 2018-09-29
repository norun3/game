package net.ktrnet.game.action.logic;

import java.awt.event.KeyEvent;

import net.ktrnet.game.action.object.GActionChara;
import net.ktrnet.game.base.GFrame;
import net.ktrnet.game.base.input.KeyStateManager;
import net.ktrnet.game.base.logic.GameLogic;
import net.ktrnet.game.base.object.GScene;
import net.ktrnet.game.base.visual.GObject;

public class ActioonGameLogic implements GameLogic {

	public ActioonGameLogic() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public int procBefore(GScene scene) {
		// 処理なし
		return GFrame.RESULT_SUCCESS;
	}

	@Override
	public int procKey(GScene scene, KeyStateManager keyman) {

		GObject gobj = scene.getFocusObject();
		if (gobj == null || !(gobj instanceof GActionChara)) {
			return GFrame.RESULT_SUCCESS;
		}
		GActionChara chara = (GActionChara)gobj;

		int result = GFrame.RESULT_SUCCESS;

		if (keyman.isPressed(KeyEvent.VK_RIGHT)) {
			System.out.println("press key right.");
			chara.walk();
		}

		if (keyman.isPressed(KeyEvent.VK_LEFT)) {
			System.out.println("press key left.");
			chara.back();
		}

		if (keyman.isPressed(KeyEvent.VK_UP)) {
			System.out.println("press key up.");
		}

		if (keyman.isPressed(KeyEvent.VK_DOWN)) {
			System.out.println("press key down.");
		}

		if (keyman.isPressed(KeyEvent.VK_SPACE)) {
			System.out.println("press key space.");
			chara.jump();
		}

		if (keyman.isPressed(KeyEvent.VK_ESCAPE)) {
			System.out.println("press key escape.");
			result = GFrame.RESULT_FAILUE;
		}

		return result;
	}

	@Override
	public int procKeyPause(GScene scene, KeyStateManager keyman) {
		// TODO 自動生成されたメソッド・スタブ
		return GFrame.RESULT_SUCCESS;
	}

	@Override
	public int procMain(GScene scene) {
		// TODO 自動生成されたメソッド・スタブ
		return GFrame.RESULT_SUCCESS;
	}

	@Override
	public int procMainPause(GScene scene) {
		// TODO 自動生成されたメソッド・スタブ
		return GFrame.RESULT_SUCCESS;
	}

	@Override
	public int procPauseStart(GScene scene) {
		// TODO 自動生成されたメソッド・スタブ
		return GFrame.RESULT_SUCCESS;
	}

	@Override
	public int procPauseEnd(GScene scene) {
		// TODO 自動生成されたメソッド・スタブ
		return GFrame.RESULT_SUCCESS;
	}

	@Override
	public void procNormalEnd(GScene scene) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void procAbnormalEnd(GScene scene) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
