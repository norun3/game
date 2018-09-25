package net.ktrnet.game.base;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import net.ktrnet.game.base.exception.GameRuntimeException;
import net.ktrnet.game.base.input.KeyStateManager;
import net.ktrnet.game.base.object.GScene;
import net.ktrnet.game.base.util.GameTime;

public class GFrame extends JFrame implements WindowListener, Runnable, KeyListener, FocusListener {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** ゲーム状態：開始後 */
	public static final int GAME_STATE_STARTED = 0x01;
	/** ゲーム状態：実行中 */
	public static final int GAME_STATE_ACTIVE = 0x02;
	/** ゲーム状態：停止中（キー入力と描画処理のみ行う） */
	public static final int GAME_STATE_PAUSE = 0x03;
	/** ゲーム状態：終了 */
	public static final int GAME_STATE_END = 0x04;
	/** ゲーム状態：エラー終了 */
	public static final int GAME_STATE_ABEND = 0x05;

	/** 処理結果コード：成功 */
	public static final int RESULT_SUCCESS = 0x01;

	/** 処理結果コード：失敗 */
	public static final int RESULT_FAILUE = 0x02;

	/** １秒（ミリ秒単位） = 1000ms */
	private static final long MILLSEC = 1000;

	/** millisecond / frame */
	private long mspframe = 1000 / 60;

	/** ゲーム状態を表す変数：ゲームループの継続条件にもなる */
	private int gameStatus = -1;

	/** ゲーム中のキー押下状態を管理する変数 */
	private KeyStateManager keyman = null;

	/** ゲームシーンリスト */
	private Map<String, GScene> scenes = null;

	/** ゲーム中のオブジェクトを管理する変数 */
	private GScene scene = null;

	/** ゲームループのメインスレッド */
	private Thread mainThread = null;

	// ここから処理クラス
	private GPanel panel = null;

	public GFrame() {
		this.panel = new GPanel();
		this.add(this.panel);
	}

	public void initialize() {

		this.gameStatus = -1;
		this.keyman = new KeyStateManager();
		this.scenes = new HashMap<String, GScene>();
		// スレッド作成
		this.mainThread = new Thread(this);

		// キー入力受付登録
		this.addKeyListener(this);

		this.setFocusable(true);

		this.startGame();
	}

	@Override
	public void run() {

		System.out.println("run start");

		// 実行前チェック処理
		if (this.scene != null) {
			// TODO : エラーメッセージ
			return;
		}

		if (this.scene.getGameLogic() == null) {
			// TODO : エラーメッセージ
			return;
		}

		// フレーム処理用変数：フレーム開始時間（ミリ秒）
		long startTime = 0;
		// フレーム処理用変数：フレーム実行時間（ミリ秒）
		long elapseTime = 0;
		// フレーム処理用変数：フレームスリープ時間（ミリ秒）
		long sleepTime = 0;
		// フレーム処理用変数：処理スキップフラグ
		boolean processSkip = false;
		// 処理結果
		int result = -1;

		result = this.scene.getGameLogic().procBefore(this.scene);
		if (result != RESULT_SUCCESS) {
			return;
		}

		// 初回フレーム処理用処理
		startTime = GameTime.getSystemTime();

		// 処理ループ
		this.gameStatus = GAME_STATE_ACTIVE;
		while(this.gameStatus == GAME_STATE_ACTIVE || this.gameStatus == GAME_STATE_PAUSE) {

			if (!processSkip) {

				if (this.gameStatus == GAME_STATE_ACTIVE) {

					result = this.scene.getGameLogic().procKey(this.scene, this.keyman);

					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

					result = this.scene.getGameLogic().procMain(this.scene);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

				} else if (this.gameStatus == GAME_STATE_PAUSE) {

					result = this.scene.getGameLogic().procKeyPause(this.scene, this.keyman);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

					result = this.scene.getGameLogic().procMainPause(this.scene);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

				}

				// 更新
				this.scene.update();

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
					this.gameStatus = GAME_STATE_ABEND;
					break;
				}
				processSkip = false;
			} else {
				processSkip = true;
			}

			startTime = GameTime.getSystemTime();
		}

		// 最終処理
		switch(this.gameStatus) {
		case GAME_STATE_END:
			this.scene.getGameLogic().procNormalEnd(scene);
			break;
		case GAME_STATE_ABEND:
			this.scene.getGameLogic().procAbnormalEnd(scene);
			break;
		default:
			throw new GameRuntimeException("ステータスが異常です(" + this.gameStatus + ")");
		}

		System.out.println("run end");
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
	 * ゲーム開始
	 * <p>
	 * ゲームループを開始する
	 * </p>
	 */
	public void startGame() {
		if (!this.mainThread.isAlive()) {
			this.mainThread.start();
			this.gameStatus = GAME_STATE_STARTED;
			this.setFocusable(true);
		}
	}

	/**
	 * ゲーム停止
	 * <p>
	 * ゲームループを停止する<br>
	 * キー入力と描画は行う
	 * </p>
	 */
	public void pauseGame() {
		if (this.mainThread.isAlive()) {
			if (this.gameStatus == GAME_STATE_ACTIVE) {
				this.gameStatus = GAME_STATE_PAUSE;
				this.scene.getGameLogic().procPauseStart(this.scene);
			}
		}
	}

	public void resumeGame() {
		if (this.mainThread.isAlive()) {
			if (this.gameStatus == GAME_STATE_PAUSE) {
				this.gameStatus = GAME_STATE_ACTIVE;
				this.scene.getGameLogic().procPauseEnd(this.scene);
			}
		}
	}

	/**
	 * ゲーム終了
	 * <p>
	 * ゲームループを正常終了させる
	 * </p>
	 */
	public void endGame() {
		if (this.mainThread.isAlive()) {
			this.gameStatus = GAME_STATE_END;
		}
	}

	/**
	 * ゲーム異常終了
	 * <p>
	 * ゲームループを異常終了させる
	 * </p>
	 */
	public void abortGame() {
		if (this.mainThread.isAlive()) {
			this.mainThread.interrupt();
		}
	}


	// ------------------------------------------------------------------------------
	// ここから外部設定用
	// ------------------------------------------------------------------------------
	public void setScene(String newid) {
		GScene scene = this.scenes.get(newid);
		this.scene = scene;
		this.panel.setScene(scene);
	}

	public Map<String, GScene> getScenes() {
		return scenes;
	}

	public GScene getScene(String id) {
		return scenes.get(id);
	}

	public String addScene(String id, GScene scene) {
		scene.setId(id);
		this.scenes.put(id, scene);
		return id;
	}

	public String addScene(GScene scene) {
		this.scenes.put(scene.getId(), scene);
		return scene.getId();
	}

	// ------------------------------------------------------------------------------
	// ここからイベント
	// ------------------------------------------------------------------------------

	// ------------------------------------------------------------------------------
	// WindowListener START
	// ------------------------------------------------------------------------------

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("window Closed.");
		this.abortGame();
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

	// ------------------------------------------------------------------------------
	// WindowListener END
	// ------------------------------------------------------------------------------


	// ------------------------------------------------------------------------------
	// KeyListener START
	// ------------------------------------------------------------------------------

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
		keyman.setPressKey(e.getKeyCode(), GameTime.getSystemTime());
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
		keyman.setReleaseKey(e.getKeyCode(), GameTime.getSystemTime());
	}

	// ------------------------------------------------------------------------------
	// KeyListener END
	// ------------------------------------------------------------------------------


	// ------------------------------------------------------------------------------
	// FocusListener START
	// ------------------------------------------------------------------------------
	@Override
	public void focusGained(FocusEvent e) {
		this.resumeGame();
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.pauseGame();
	}
	// ------------------------------------------------------------------------------
	// FocusListener END
	// ------------------------------------------------------------------------------




}
