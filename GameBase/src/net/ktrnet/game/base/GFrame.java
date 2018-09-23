package net.ktrnet.game.base;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GFrame extends JFrame implements WindowListener {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;


	private GPanel panel = null;

	public GFrame() {
		this.panel = new GPanel();
		this.add(this.panel);
	}

	public void startGame() {
		this.panel.init();
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
