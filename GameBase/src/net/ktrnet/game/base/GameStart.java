package net.ktrnet.game.base;

import javax.swing.JFrame;

import net.ktrnet.game.base.exception.GameXmlException;
import net.ktrnet.game.base.util.SystemInfo;
import net.ktrnet.game.base.xml.GameSettingXml;

public class GameStart {

	public static void main(String[] args) throws Exception {

		// XMLを読み込みパラメタを取得する
		GameStart gameStartClass = new GameStart();
		gameStartClass.loadGameSetting();
		gameStartClass.classInstanced();
		gameStartClass.execute();
	}

	private GFrame frame = null;

	public GameStart() {
		this.frame = new GFrame();
		frame.setSize(500, 500);
	}

	public void classInstanced() {
		SystemInfo.setFrame(this.frame);
	}

	public void loadGameSetting() throws GameXmlException {

		this.frame = GameSettingXml.loadGFrame();
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void execute() {
		this.frame.setVisible(true);
		this.frame.initialize();
	}

}
