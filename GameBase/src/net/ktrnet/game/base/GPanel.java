package net.ktrnet.game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.JPanel;

import net.ktrnet.game.base.exception.GameRuntimeException;
import net.ktrnet.game.base.input.GameKeyEvent;
import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.object.GObjectManager;
import net.ktrnet.game.base.util.GameTime;

public class GPanel extends JPanel implements Runnable, KeyListener {

	/** ゲーム状態：開始後 */
	public static final int GAME_STATE_STARTED = 0x01;
	/** ゲーム状態：実行中 */
	public static final int GAME_STATE_ACTIVE = 0x02;
	/** ゲーム状態：終了 */
	public static final int GAME_STATE_END = 0x01;
	/** ゲーム状態：エラー終了 */
	public static final int GAME_STATE_ABEND = 0x01;

	/** １秒（ミリ秒単位） = 1000ms */
	private static long MILLSEC = 1000;

	/** millisecond / frame */
	private long mspframe = 1000 / 60;

	/** ゲームループを続けることを表す変数 */
	private boolean continued = true;

	/** ゲーム中のキー押下状態を管理する変数 */
	private Map<Integer, GameKeyEvent> keyStateMap = null;

	/** ゲーム中のオブジェクトを管理する変数 */
	private GObjectManager gobjman = null;

	public GPanel() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GPanel(LayoutManager layout) {
		super(layout);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public GPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void run() {

		// フレーム処理用変数：フレーム開始時間（ミリ秒）
		long startTime = 0;
		// フレーム処理用変数：フレーム実行時間（ミリ秒）
		long elapseTime = 0;
		// フレーム処理用変数：フレームスリープ時間（ミリ秒）
		long sleepTime = 0;
		// フレーム処理用変数：処理スキップフラグ
		boolean processSkip = false;

		// 初回フレーム処理用処理
		startTime = GameTime.getSystemTime();;

		// 処理ループ
		while(continued) {

			if (!processSkip) {

				// 前処理
				preKeyProccess(this.gobjman);

				// キー処理
				keyProccess(this.gobjman, this.keyStateMap);

				// 後処理
				postKeyProccess(this.gobjman);

				// 描画更新
				this.repaint();

			}

			// フレーム調整
			elapseTime = GameTime.getSystemTime() - startTime;
			sleepTime = mspframe - elapseTime;
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					throw new GameRuntimeException(e);
				}
				processSkip = false;
			} else {
				processSkip = true;
			}

			startTime = GameTime.getSystemTime();
		}

	}

	/**
	 * FPS設定
	 * <p>
	 * 1秒あたりの描画フレーム数（Frame Per Seccond）を設定する。<br>
	 * </p>
	 * @param fps FPS
	 */
	public void setFps(int fps) {
		this.mspframe = (long)((double)MILLSEC / (double)fps);
	}

	/**
	 * キー処理前処理
	 * <p>
	 * ゲームループにて、キー処理前に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int preKeyProccess(GObjectManager gobjman) {
		// 子クラスで実装
		return GAME_STATE_ACTIVE;
	}

	/**
	 * キー処理
	 * <p>
	 * ゲームループにて、キー処理を行うメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @param keyStateMap キー状態管理クラス
	 * @return ゲーム状態
	 */
	protected int keyProccess(GObjectManager gobjman, Map<Integer, GameKeyEvent> keyStateMap) {
		// 子クラスで実装
		return GAME_STATE_ACTIVE;
	}

	/**
	 * キー処理後処理
	 * <p>
	 * ゲームループにて、キー処理後に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int postKeyProccess(GObjectManager gobjman) {
		// 子クラスで実装
		return GAME_STATE_ACTIVE;
	}

	/**
	 * キータイプイベント
	 * <p>
	 * KeyListenerの実装<br>
	 * このパネルでキータイプされたときに発生するイベント<br>
	 * 処理なし
	 * </p>
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Typedは処理なし
	}

	/**
	 * キー押下イベント
	 * <p>
	 * KeyListenerの実装<br>
	 * このパネルでキーが押下されたときに発生するイベント<br>
	 * キー状態マップに押下された状態を格納する
	 * </p>
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		// まだキーイベントが消化されていない場合
		if (keyStateMap.containsKey(e.getKeyCode())) {

			GameKeyEvent gke = keyStateMap.get(e.getKeyCode());

			switch (gke.getKeyState()) {
			// 前回から押下状態のままの場合、何もしない
			case KeyEvent.KEY_PRESSED:
				// 処理なし
				break;
			// 前回が離されていた場合、押下状態にする
			case KeyEvent.KEY_RELEASED:
				gke.setKeyState(KeyEvent.KEY_PRESSED);
				gke.setKeyPressTime(GameTime.getSystemTime());
				gke.setKeyReleaseTime(0);
				break;
			}

		// キーイベント消化or最初のキー押下の場合
		} else {
			// 新しく押下状態で登録する
			GameKeyEvent gke = new GameKeyEvent(e.getKeyCode(), KeyEvent.KEY_PRESSED);
			gke.setKeyPressTime(GameTime.getSystemTime());
			gke.setKeyReleaseTime(0);
			keyStateMap.put(e.getKeyCode(), gke);
		}
	}

	/**
	 * キー離反イベント
	 * <p>
	 * KeyListenerの実装<br>
	 * このパネルでキーが離されたときに発生するイベント<br>
	 * キー状態マップに離反した状態を格納する
	 * </p>
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		// まだキーイベントが消化されていない場合
		if (keyStateMap.containsKey(e.getKeyCode())) {

			GameKeyEvent gke = keyStateMap.get(e.getKeyCode());

			switch (gke.getKeyState()) {
			// 前回が押下状態の場合、離れた状態にする
			case KeyEvent.KEY_PRESSED:
				gke.setKeyState(KeyEvent.KEY_RELEASED);
				gke.setKeyPressTime(0);
				gke.setKeyReleaseTime(GameTime.getSystemTime());
				break;
			// 前回から離されていた場合、なにもしない
			case KeyEvent.KEY_RELEASED:
				// 処理なし
				break;
			}

		// キーイベント消化or最初のキー離しの場合
		} else {
			// 新しく離した状態で登録する
			GameKeyEvent gke = new GameKeyEvent(e.getKeyCode(), KeyEvent.KEY_RELEASED);
			gke.setKeyPressTime(0);
			gke.setKeyReleaseTime(GameTime.getSystemTime());
			keyStateMap.put(e.getKeyCode(), gke);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Color c = null;

		Graphics2D g2d = (Graphics2D)g;

		c = g2d.getColor();

		// 背景描画

		// 背景画像が指定されている場合、画像を描画
		if (gobjman.getBackImage() != null) {

			g2d.drawImage(gobjman.getBackImage(),
					0, 0, this.getWidth(), this.getHeight(), Color.BLACK, null);

		// 背景画像が指定されてなく背景色が指定されている場合、背景色を描画
		} else if (gobjman.getBackColor() != null) {
			g2d.setColor(gobjman.getBackColor());
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		// 画像も色も指定されていない場合は、黒色で描画
		} else {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		// オブジェクト描画
		for (int layer : gobjman.getLayeres()) {
			for (GObject gobj : gobjman.getGameObjects(layer)) {
				g2d.drawImage(gobj.getImage(),
						gobj.getX(), gobj.getY(),
						gobj.getWidth(), gobj.getHeight(), null);
			}
		}

		g2d.setColor(c);
	}


}
