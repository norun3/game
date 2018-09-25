package net.ktrnet.game.base.visual;

import net.ktrnet.game.base.logic.GUpdate;
import net.ktrnet.game.base.logic.GameWorldLogic;

public class GMainGround extends GObjectList implements GDraw, GUpdate {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private GameWorldLogic gameWorldLogic = null;

	@Override
	public void update() {
		if (this.gameWorldLogic != null) {
			this.gameWorldLogic.execute(this);
		}
		super.update();
	}

	public GameWorldLogic getGameWorldLogic() {
		return gameWorldLogic;
	}

	public void setGameWorldLogic(GameWorldLogic gameWorldLogic) {
		this.gameWorldLogic = gameWorldLogic;
	}

}
