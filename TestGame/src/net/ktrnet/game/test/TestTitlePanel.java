package net.ktrnet.game.test;

import java.awt.Image;
import java.awt.event.KeyEvent;

import net.ktrnet.game.base.GPanel;
import net.ktrnet.game.base.input.KeyStateManager;
import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.object.GObjectManager;
import net.ktrnet.game.base.util.GameTime;
import net.ktrnet.game.base.util.ResourceUtil;

public class TestTitlePanel extends GPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private ResourceUtil resourceUtil = null;

	private Image imgCharacter001 = null;
	private int idxCharacter001 = -1;

	public TestTitlePanel() {
		super();
		this.resourceUtil = new ResourceUtil();
		imgCharacter001 = this.resourceUtil.getImage("jashinchan");
	}



	@Override
	protected int procBefore(GObjectManager gobjman) {

		GCharacter chara1 = new GCharacter(10, 300, 0.1, imgCharacter001);
		idxCharacter001 = gobjman.addGameObject(0, chara1);
		System.out.println("be visible character001.");

		return RESULT_SUCCESS;
	}


	@Override
	protected int procKey(GObjectManager gobjman, KeyStateManager keyman) {

		int result = GPanel.RESULT_SUCCESS;

		if (keyman.isPressed(KeyEvent.VK_RIGHT)) {
			System.out.println("press key right.");
			if (idxCharacter001 != -1) {
				GCharacter gchara = (GCharacter)gobjman.getGameObject(idxCharacter001);
				gchara.walk();
				System.out.println("character001 moved right 10px.");
			}
		}

		if (keyman.isPressed(KeyEvent.VK_LEFT)) {
			System.out.println("press key left.");
			if (idxCharacter001 != -1) {
				GCharacter gchara = (GCharacter)gobjman.getGameObject(idxCharacter001);
				gchara.back();
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
				GCharacter gchara = (GCharacter)gobjman.getGameObject(idxCharacter001);
				gchara.jump();
			}
		}

		if (keyman.isPressed(KeyEvent.VK_ESCAPE)) {
			System.out.println("press key escape.");
			result = GPanel.RESULT_FAILUE;
		}

		return result;
	}

	@Override
	protected int procMain(GObjectManager gobjman) {

		for (GObject gobj : gobjman.getGameObjects()) {

			if (gobj instanceof GCharacter) {
				GCharacter gchar = (GCharacter)gobj;
				gchar.update(GameTime.getSystemTime());
			}
		}

		return super.procMain(gobjman);
	}

	@Override
	protected void procNormalEnd(GObjectManager gobjman) {
		System.out.println("lastEndProccess.");
	}

	@Override
	protected void procAbnormalEnd(GObjectManager gobjman) {
		System.out.println("lastAbendProccess.");
	}



}
