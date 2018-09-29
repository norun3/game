package net.ktrnet.game.action.logic;

import net.ktrnet.game.action.object.GActionChara;
import net.ktrnet.game.base.GFrame;
import net.ktrnet.game.base.logic.GameWorldLogic;
import net.ktrnet.game.base.util.GameTime;
import net.ktrnet.game.base.util.SystemInfo;
import net.ktrnet.game.base.visual.GObject;
import net.ktrnet.game.base.visual.GObjectList;

public class GActionWorldLogic implements GameWorldLogic {

	/** 重力加速度[px/ms] */
	private final static double G = 0.0098;

	private long preCalcTime = 0;

	@Override
	public int execute(GObjectList gobjects) {

		if (preCalcTime == 0) {
			preCalcTime = GameTime.getSystemTime();
			return GFrame.RESULT_SUCCESS;
		}

		long curCalcTime = GameTime.getSystemTime();
		long elapseTime = curCalcTime - preCalcTime;

		gobjects.forEach(gobj -> freeFall(gobj, elapseTime, gobjects));

		preCalcTime = curCalcTime;

		return 0;
	}

	private void freeFall(GObject gobj, long elapseTime, GObjectList gobjects) {

		if (!(gobj instanceof GActionChara)) {
			return;
		}

		GActionChara chara = (GActionChara)gobj;

		double height = 0;

		// 高さ取得
		height = calcHeight(chara, gobjects);

		// 接地していなかったら自由落下
		if (height > 0) {

			// 落下距離
			double fallHeight = calcFreeFallHeight(elapseTime);

			double nextY = chara.getY() + fallHeight;
			chara.setY(nextY);

			System.out.println("free falling.[" + fallHeight + "] -> nextY [" + nextY + "]");
		}

		return;
	}

	/**
	 * 高さ取得
	 * <p>
	 * 該当キャラについて、現在の高さを計算する。<br>
	 * ※高さ＝そのキャラの縦位置 － そのキャラの下方向に直近の衝突可能オブジェクトの縦位置<br>
	 * </p>
	 *
	 * @param chara 高さを計算するキャラクタオブジェクト
	 * @param gobjects 検索する対象となる世界に存在するオブジェクト群
	 * @return 高さ
	 */
	private double calcHeight(GActionChara chara, GObjectList gobjects) {

		double ya = chara.getY() + chara.getHeight();

		// TODO 仮にウィンドウ底辺を直近オブジェクトとする
		double yb = SystemInfo.getInstance().getWindowSize().getHeight();

		// 高さ計算
		double height = yb - ya;

		// 高さが0未満のときは0に近似
		if (height < 0) {
			height = 0;
		}

		return height;
	}

	/**
	 * 自由落下距離算出
	 * <p>
	 * 自由落下しているときの落下距離を算出する。<br>
	 * dh＝ 1/2 * g * t^2<br>
	 * ※t:経過時間、dh:落下距離、g:重力加速度<br>
	 * </p>
	 * @param t 経過時間
	 * @return 落下距離
	 */
	private double calcFreeFallHeight(double t){

		// 経過時間(t)から落下距離(dh)を算出
		// dh = 1/2 * g * t^2
		double dh = G * Math.pow(t, 2) / 2;

		return dh;
	}
}
