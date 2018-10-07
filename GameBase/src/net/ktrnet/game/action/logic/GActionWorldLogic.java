package net.ktrnet.game.action.logic;

import net.ktrnet.game.action.object.GActionChara;
import net.ktrnet.game.base.GFrame;
import net.ktrnet.game.base.logic.GameWorldLogic;
import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.util.GameTime;
import net.ktrnet.game.base.util.SystemInfo;
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

		if (!chara.isFixed()) {

			double height = 0;

			// 高さ取得
			height = calcHeight(chara, gobjects);

			// 接地していなかったら自由落下
			if (height > 0) {

				/*
	移動距離＝物体速度×単位時間＋０．５×重力加速度×単位時間^2
	現在位置＝現在位置＋（経過時間／単位時間）×移動距離

	物体速度＝物体速度＋重力加速度×経過時間

	現在位置を出力
				 */

				double fallInitVelocity = chara.getVelocity() * SystemInfo.getFrameSec();
				double fallPerSec = 0.5f * G * Math.pow(SystemInfo.getFrameSec(), 2);
				fallPerSec =  fallInitVelocity + fallPerSec;
				double fallElapse = (elapseTime / SystemInfo.getFrameSec()) * fallPerSec;
				double nextY = chara.getY() + fallElapse;

				// 速度計算
				double velocityPerSec = G * SystemInfo.getFrameSec();
				double velocityElapse = (elapseTime / SystemInfo.getFrameSec()) * velocityPerSec;
				double nextVelocity = chara.getVelocity() + velocityElapse;

				chara.setY(nextY);
				chara.setVelocity(nextVelocity);

				System.out.println("fall down fall height [" + fallElapse + "] speed [" + velocityElapse + "]");

				height = calcHeight(chara, gobjects);
				if (height == 0) {
					chara.setVelocity(0);
					chara.setJumping(false);
				}
			}

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
		double yb = SystemInfo.getCanvasHeight();

		// 高さ計算
		double height = yb - ya;

		// 高さが0未満のときは0に近似
		if (height < 0) {
			height = 0;
		}

		return height;
	}


}
