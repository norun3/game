package net.ktrnet.game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.ktrnet.game.base.object.GScene;

public class GPanel extends JPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private GScene scene = null;

	public GPanel() {
	}

	public GPanel(LayoutManager layout) {
		super(layout);
	}

	public GPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public GPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Color c = null;

		Graphics2D g2d = (Graphics2D)g;

		c = g2d.getColor();

		// 描画処理
		if (scene != null) {
			scene.draw(g2d);
		}

		g2d.setColor(c);
	}

	public GScene getScene() {
		return scene;
	}

	public void setScene(GScene scene) {
		this.scene = scene;
	}

}
