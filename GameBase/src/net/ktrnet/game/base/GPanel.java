package net.ktrnet.game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import net.ktrnet.game.base.exception.GameRuntimeException;
import net.ktrnet.game.base.input.KeyStateManager;
import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.object.GObjectManager;
import net.ktrnet.game.base.util.GameTime;

public class GPanel extends JPanel implements Runnable, KeyListener, HierarchyListener, FocusListener {

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

	/** ゲーム中のオブジェクトを管理する変数 */
	private GObjectManager gobjman = null;

	/** ゲームループのメインスレッド */
	private Thread mainThread = null;

	public GPanel() {
		this.init();
	}

	public GPanel(LayoutManager layout) {
		super(layout);
		this.init();
	}

	public GPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.init();
	}

	public GPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.init();
	}

	/**
	 * 初期化処理
	 * <p>
	 * クラス内変数の初期化処理を行う。
	 * </p>
	 */
	private void init() {
		this.gameStatus = -1;
		this.keyman = new KeyStateManager();
		this.gobjman = new GObjectManager();
		// スレッド作成
		this.mainThread = new Thread(this);

		// キー入力受付登録
		this.addKeyListener(this);
		// 階層コンポーネント変更イベント受付登録
		this.addHierarchyListener(this);

		this.setFocusable(true);
	}

	@Override
	public void run() {

		System.out.println("run start");

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

		result = procBefore(this.gobjman);
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

					result = procKey(this.gobjman, this.keyman);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

					result = procMain(this.gobjman);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

				} else if (this.gameStatus == GAME_STATE_PAUSE) {

					result = procKeyPause(this.gobjman, this.keyman);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

					result = procMainPause(this.gobjman);
					// 処理結果＝成功　以外の場合、ループ終了
					if (result != RESULT_SUCCESS) {
						this.gameStatus = GAME_STATE_ABEND;
						break;
					}

				}

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
			procNormalEnd(gobjman);
			break;
		case GAME_STATE_ABEND:
			procAbnormalEnd(gobjman);
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
	 * 前処理
	 * <p>
	 * ゲームループ前に実行されるメソッド<br>
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int procBefore(GObjectManager gobjman) {
		// 子クラスで実装
		return RESULT_SUCCESS;
	}

	/**
	 * キー処理
	 * <p>
	 * ゲームループにて、キー処理を行うメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @param keyman キー状態管理クラス
	 * @return ゲーム状態
	 */
	protected int procKey(GObjectManager gobjman, KeyStateManager keyman) {
		// 子クラスで実装
		return RESULT_SUCCESS;
	}

	/**
	 * キー処理（Pause時）
	 * <p>
	 * ゲームループにて、PAUSE中のキー処理を行うメソッド<br>
	 * デフォルトでは通常のキー処理を行う<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @param keyman キー状態管理クラス
	 * @return ゲーム状態
	 */
	protected int procKeyPause(GObjectManager gobjman, KeyStateManager keyman) {
		// 子クラスで実装
		return procKey(gobjman, keyman);
	}

	/**
	 * メイン処理
	 * <p>
	 * ゲームループにて、キー処理後に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int procMain(GObjectManager gobjman) {
		// 子クラスで実装
		return RESULT_SUCCESS;
	}

	/**
	 * メイン処理（Pause時）
	 * <p>
	 * ゲームループにて、PAUSE中のメイン処理を行うメソッド<br>
	 * デフォルトでは通常のメイン処理を行う<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int procMainPause(GObjectManager gobjman) {
		// 子クラスで実装
		return procMain(gobjman);
	}

	/**
	 * PAUSE発生時処理
	 * <p>
	 * pauseGame()呼び出し時に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int procPauseStart(GObjectManager gobjman) {
		// 子クラスで実装
		return RESULT_SUCCESS;
	}

	/**
	 * PAUSE終了時処理
	 * <p>
	 * resumeGame()呼び出し時に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	protected int procPauseEnd(GObjectManager gobjman) {
		// 子クラスで実装
		return RESULT_SUCCESS;
	}


	/**
	 * 正常時最終処理
	 * <p>
	 * ゲームループ終了後、正常終了の場合に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 */
	protected void procNormalEnd(GObjectManager gobjman) {
		return;
	}

	/**
	 * 異常時最終処理
	 * <p>
	 * ゲームループ終了後、異常終了の場合に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 */
	protected void procAbnormalEnd(GObjectManager gobjman) {
		return;
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
				procPauseStart(this.gobjman);
			}
		}
	}

	public void resumeGame() {
		if (this.mainThread.isAlive()) {
			if (this.gameStatus == GAME_STATE_PAUSE) {
				this.gameStatus = GAME_STATE_ACTIVE;
				procPauseEnd(this.gobjman);
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

	/**
	 * 階層変更イベント
	 * <p>
	 * 親コンポーネントに変更があったときに通知されるイベント<br>
	 * 親コンポーネント変更により自身が非表示担った場合に、<br>
	 * 自身がもつスレッドを終了させる
	 * </p>
	 */
	@Override
	public void hierarchyChanged(HierarchyEvent e) {
		//check for Hierarchy event
		if(e.getChangeFlags() == HierarchyEvent.DISPLAYABILITY_CHANGED) {
			//do the required action upon close
			if(!this.isDisplayable()) {
				if (this.mainThread.isAlive()) {
					this.mainThread.interrupt();
				}
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		this.resumeGame();
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.pauseGame();
	}

}
