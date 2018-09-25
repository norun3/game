package net.ktrnet.game.test;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import net.ktrnet.game.base.GPanel;
import net.ktrnet.game.base.input.KeyStateManager;
import net.ktrnet.game.base.object.GScene;
import net.ktrnet.game.base.util.ResourceUtil;

public class TestTitlePanel extends GPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private ResourceUtil resourceUtil = null;

	private GCharacter chara1 = null;
	private Image imgCharacter001 = null;
	private int idxCharacter001 = -1;

	public TestTitlePanel() {
		super();
		this.resourceUtil = new ResourceUtil();
		imgCharacter001 = this.resourceUtil.getImage("jashinchan");
	}



	@Override
	protected int procBefore(GScene graphic) {

		// 背景設定
		graphic.setBackgroundColor(Color.BLUE);

		// キャラクター追加
		chara1 = new GCharacter(10, 300, 0.1, imgCharacter001);
		graphic.addObject(chara1);
		System.out.println("be visible character001.");

		return RESULT_SUCCESS;
	}


	@Override
	protected int procKey(GScene graphic, KeyStateManager keyman) {

		int result = GPanel.RESULT_SUCCESS;

		if (keyman.isPressed(KeyEvent.VK_RIGHT)) {
			System.out.println("press key right.");
			if (idxCharacter001 != -1) {
				chara1.walk();
				System.out.println("character001 moved right 10px.");
			}
		}

		if (keyman.isPressed(KeyEvent.VK_LEFT)) {
			System.out.println("press key left.");
			if (idxCharacter001 != -1) {
				chara1.back();
				System.out.println("character001 moved left 10px.");
			}
		}

		if (keyman.isPressed(KeyEvent.VK_UP)) {
			System.out.println("press key up.");
			if (idxCharacter001 != -1) {
//				GObject gobj = gobjman.getGameObject(idxCharacter001);
//				gobj.setY(gobj.getY() - 10);
//				System.out.println("character001 moved up 10px.");
			}
		}

		if (keyman.isPressed(KeyEvent.VK_DOWN)) {
			System.out.println("press key down.");
			if (idxCharacter001 != -1) {
//				GObject gobj = gobjman.getGameObject(idxCharacter001);
//				gobj.setY(gobj.getY() + 10);
//				System.out.println("character001 moved down 10px.");
			}
		}

		if (keyman.isPressed(KeyEvent.VK_SPACE)) {
			System.out.println("press key space.");
			if (idxCharacter001 != -1) {
				chara1.jump();
			}
		}

		if (keyman.isPressed(KeyEvent.VK_ESCAPE)) {
			System.out.println("press key escape.");
			result = GPanel.RESULT_FAILUE;
		}

		return result;
	}

	@Override
	protected int procMain(GScene graphic) {

//		for (GObject gobj : gobjman.getGameObjects()) {
//
//			if (gobj instanceof GCharacter) {
//				GCharacter gchar = (GCharacter)gobj;
//				gchar.update(GameTime.getSystemTime());
//			}
//		}
//
//		return super.procMain(gobjman);
		return super.procMain(graphic);
	}

	@Override
	protected void procNormalEnd(GScene graphic) {
		System.out.println("lastEndProccess.");
	}

	@Override
	protected void procAbnormalEnd(GScene graphic) {
		System.out.println("lastAbendProccess.");
	}



}
