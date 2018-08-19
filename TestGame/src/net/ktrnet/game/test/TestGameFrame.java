package net.ktrnet.game.test;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.ktrnet.game.base.GFrame;

public class TestGameFrame extends GFrame implements WindowListener {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Thread mainThread = null;

	private TestTitlePanel panel = null;

	public TestGameFrame() {
		this.panel = new TestTitlePanel();
		this.add(this.panel);
	}

	public void startGame() {
		mainThread = new Thread(this.panel);
		mainThread.start();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("window Closed.");
		if (mainThread.isAlive()) {
			mainThread.interrupt();
		}
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
