package net.ktrnet.game.test;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.ktrnet.game.base.GFrame;

public class TestGameFrame extends GFrame implements WindowListener {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private TestTitlePanel panel = null;

	public TestGameFrame() {
		this.panel = new TestTitlePanel();
		this.add(this.panel);
	}

	public void startGame() {
		this.panel.startGame();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("window Closed.");
		this.panel.abortGame();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}


}
