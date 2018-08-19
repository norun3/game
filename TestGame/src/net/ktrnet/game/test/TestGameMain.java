package net.ktrnet.game.test;

import javax.swing.JFrame;

public class TestGameMain {

	public static void main(String[] args) {

		TestGameFrame frame = new TestGameFrame();

		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}

}
