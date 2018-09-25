package net.ktrnet.game.base.util;

public class TypeUtil {

	public static boolean isNumber(String text) {

		Integer v = parseInteger(text);

		return v == null ? false : true;
	}

	public static Integer parseInteger(String text) {

		Integer v = null;

		if (text == null || text.isEmpty()) {
			return null;
		}

		try {
			v = Integer.parseInt(text);
		} catch (NumberFormatException e) {
			v = null;
		}

		return v;
	}

	public static Boolean parseBoolean(String text) {

		Boolean b = null;

		if (text == null || text.isEmpty()) {
			return null;
		}

		b = Boolean.parseBoolean(text);

		return b;
	}
}
