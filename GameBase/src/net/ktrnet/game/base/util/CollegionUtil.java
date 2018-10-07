package net.ktrnet.game.base.util;

import java.awt.Shape;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;

/**
 * 衝突判定用ユーティリティ
 * @author norn
 *
 */
public class CollegionUtil {

	public CollegionUtil() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static boolean hit(Shape shape1, Shape shape2) {

		boolean isHit = false;

		PathIterator i = new FlatteningPathIterator(shape2.getPathIterator(null), 1d);
		float[] coords = new float[6];
		while (!i.isDone()) {
			i.currentSegment(coords);


			isHit = shape1.contains(coords[0], coords[1]);
			if (isHit) {
				return true;
			}

			i.next();
		}

		return false;
	}

}
