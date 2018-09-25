package net.ktrnet.game.base.exception;

import org.w3c.dom.Element;

public class GameXmlException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GameXmlException() {
	}

	public GameXmlException(String path, Element e, String attrName, String message) {
		this(path, e, attrName, message, null);
	}

	public GameXmlException(String path, Element e, String attrName, String message, Throwable cause) {
		super(structMessage(path, e, attrName, message), cause);
	}

	private static String structMessage(String path, Element e, String attrName, String message) {

		StringBuffer sbMessage = new StringBuffer();

		if (path == null) {
			sbMessage.append("unknown xml path");
		} else {
			sbMessage.append(path);
		}
		sbMessage.append(" ");

		if (e != null && e.getNamespaceURI() != null) {
			sbMessage.append("[");
			sbMessage.append(e.getNamespaceURI());
			if (attrName != null) {
				sbMessage.append(":");
				sbMessage.append(attrName);
			}
			sbMessage.append("]");
			sbMessage.append(" ");
		}

		if (message != null) {
			sbMessage.append(message);
		}

		return sbMessage.toString();
	}
}
