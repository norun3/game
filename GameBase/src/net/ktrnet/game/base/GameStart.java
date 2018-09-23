package net.ktrnet.game.base;

import java.net.URL;

import javax.swing.JFrame;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.ktrnet.game.base.util.SystemInfo;
import net.ktrnet.game.base.util.TypeUtil;
import net.ktrnet.game.base.util.XmlUtil;

public class GameStart {

	public static void main(String[] args) {

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
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void classInstanced() {
		SystemInfo.getInstance().setFrame(this.frame);
	}

	public void loadGameSetting() {

		// 設定XMLファイルの検索
		URL pathResourceXml = XmlUtil.findXml("GameSetting.xml");
		if (pathResourceXml == null) {
			// エラーメッセージ
			System.err.println("GameSetting.xml is not found.");
			return;
		}

		// XMLを読み込みリソースを取得する
		Document doc = XmlUtil.getXmlDocument(pathResourceXml);

		// XMLの解析開始
		Element root = doc.getDocumentElement();

		if (!"GameSetting".equals(root.getTagName())) {
			return;
		}

		String winTitle = XmlUtil.getElementValue(root, "window.title");
		if (winTitle != null) {
			this.frame.setTitle(winTitle);
		}
		String winWidth = XmlUtil.getElementValue(root, "window.width");
		Integer intWinWidth = TypeUtil.parseInteger(winWidth);
		String winHeight = XmlUtil.getElementValue(root, "window.height");
		Integer intWinHeight = TypeUtil.parseInteger(winHeight);
		if (intWinWidth != null && intWinHeight != null) {
			this.frame.setSize(intWinWidth, intWinHeight);
			this.frame.setLocationRelativeTo(null);
		}

	}

	public void execute() {
		this.frame.setVisible(true);
		this.frame.startGame();
	}

}
