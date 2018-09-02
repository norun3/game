package net.ktrnet.game.base.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResourceUtil {

	public ResourceUtil() {

		// 設定XMLファイルの検索
		URL pathResourceXml = XmlUtil.findXml("Resources.xml");
		if (pathResourceXml == null) {
			// エラーメッセージ
			return;
		}

		// XMLを読み込みリソースを取得する
		Document docResourceXml = XmlUtil.getXmlDocument(pathResourceXml);

		// XMLの解析開始
		Element e = docResourceXml.getDocumentElement();

		if ("resouces".equals(e.getNodeName())) {

			NodeList children = e.getChildNodes();
			for (int i = 0 ; i < children.getLength() ; i++) {

				Node chnode = (Element)children.item(i);
				if (chnode.getNodeType() == Node.ELEMENT_NODE) {

				}
			}
		}



	}

	public static BufferedImage loadImage(String path) throws IOException {

		BufferedImage bufImg = null;
		URL url = ResourceUtil.class.getClassLoader().getResource(path);
		bufImg = ImageIO.read(url);

		return bufImg;
	}
}
