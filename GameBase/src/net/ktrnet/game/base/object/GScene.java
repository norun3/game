package net.ktrnet.game.base.object;

import java.awt.Graphics2D;
import java.util.List;
import java.util.stream.Collectors;

import net.ktrnet.game.base.logic.GUpdate;
import net.ktrnet.game.base.logic.GameLogic;
import net.ktrnet.game.base.visual.GBackGround;
import net.ktrnet.game.base.visual.GDraw;
import net.ktrnet.game.base.visual.GFrontGround;
import net.ktrnet.game.base.visual.GMainGround;
import net.ktrnet.game.base.visual.GObject;

public class GScene implements GDraw, GUpdate {

	private String id = null;

	private GBackGround backGround = null;

	private GMainGround mainGround = null;

	private GFrontGround frontGround= null;

	private GameLogic gameLogic = null;

	private GObject focusObject = null;

	public GScene() {
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (this.backGround != null) {
			this.backGround.draw(g2d);
		}

		if (this.mainGround != null) {
			this.mainGround.draw(g2d);
		}

		if (this.frontGround != null) {
			this.frontGround.draw(g2d);
		}

	}

	@Override
	public void update() {

		if (this.backGround != null) {
			this.backGround.update();
		}

		if (this.mainGround != null) {
			this.mainGround.update();
		}

		if (this.frontGround != null) {
			this.frontGround.update();
		}

	}

	public GObject getFocusObject() {
		return focusObject;
	}

	public void setFocusObject(String id) {

		GObject gobj = this.getObject(id);
		if (gobj != null) {
			this.focusObject = gobj;
		}
	}

	public GObject getObject(String id) {

		if (id == null || id.isEmpty()) {
			return null;
		}

		if (id.equals(this.backGround.getId())) {
			return this.backGround;
		}

		List<GObject> filteredList = null;

		filteredList = this.mainGround.stream().filter(e -> id.equals(e.getId())).collect(Collectors.toList());
		if (filteredList.size() > 0) {
			return filteredList.get(0);
		}

		filteredList = this.frontGround.stream().filter(e -> id.equals(e.getId())).collect(Collectors.toList());
		if (filteredList.size() > 0) {
			return filteredList.get(0);
		}

		return null;
	}

	// ----------------------------------------------------------------------------
	// Getter/Setter START
	// ----------------------------------------------------------------------------

	public GBackGround getBackGround() {
		return backGround;
	}

	public void setBackGround(GBackGround backGround) {
		this.backGround = backGround;
	}

	public GMainGround getMainGround() {
		return mainGround;
	}

	public void setMainGround(GMainGround mainGround) {
		this.mainGround = mainGround;
	}

	public GFrontGround getFrontGround() {
		return frontGround;
	}

	public void setFrontGround(GFrontGround frontGround) {
		this.frontGround = frontGround;
	}

	public GameLogic getGameLogic() {
		return gameLogic;
	}

	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
	// ----------------------------------------------------------------------------
	// Getter/Setter END
	// ----------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
