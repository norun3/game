package net.ktrnet.game.base.logic;

import net.ktrnet.game.base.input.KeyStateManager;
import net.ktrnet.game.base.object.GScene;

public interface GameLogic {

	/**
	 * 前処理
	 * <p>
	 * ゲームループ前に実行されるメソッド<br>
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	public int procBefore(GScene scene);

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
	public int procKey(GScene scene, KeyStateManager keyman);

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
	public int procKeyPause(GScene scene, KeyStateManager keyman);

	/**
	 * メイン処理
	 * <p>
	 * ゲームループにて、キー処理後に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	public int procMain(GScene scene);

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
	public int procMainPause(GScene scene);

	/**
	 * PAUSE発生時処理
	 * <p>
	 * pauseGame()呼び出し時に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	public int procPauseStart(GScene scene);

	/**
	 * PAUSE終了時処理
	 * <p>
	 * resumeGame()呼び出し時に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 * @return ゲーム状態
	 */
	public int procPauseEnd(GScene scene);

	/**
	 * 正常時最終処理
	 * <p>
	 * ゲームループ終了後、正常終了の場合に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 */
	public void procNormalEnd(GScene scene);

	/**
	 * 異常時最終処理
	 * <p>
	 * ゲームループ終了後、異常終了の場合に呼び出されるメソッド<br>
	 * 子クラスにて実装する
	 * </p>
	 * @param gobjman ゲームオブジェクト管理クラス
	 */
	public void procAbnormalEnd(GScene scene);

}
