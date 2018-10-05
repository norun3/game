package net.ktrnet.game.base.visual;

import java.awt.Graphics2D;

import net.ktrnet.game.base.logic.GUpdate;
import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.object.LayerList;

public class GObjectList extends LayerList<GObject> implements GDraw, GUpdate {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GObjectList() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void draw(Graphics2D g2d) {
		this.forEach(e -> e.draw(g2d));
	}

	@Override
	public void update() {
		this.forEach(e -> e.update());
	}

}
