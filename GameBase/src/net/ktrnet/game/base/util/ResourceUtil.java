package net.ktrnet.game.base.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ResourceUtil {

	private static final String TAG_NAME_ROOT = "resources";

	private static final String TAG_NAME_CHILD = "resource";

	private static final String ATTR_NAME_CHILD_ID = "id";

	private static final String ATTR_NAME_CHILD_PATH = "path";

	private Map<String, BufferedImage> loadImages = null;

	public ResourceUtil() {

		String id = null;
		String path = null;

		this.loadImages = new HashMap<String, BufferedImage>();

		// 設定XMLファイルの検索
		URL pathResourceXml = XmlUtil.findXml("Resources.xml");
		if (pathResourceXml == null) {
			// エラーメッセージ
			// TODO
			System.err.println("Resources.xml is not found.");
			return;
		}

		// XMLを読み込みリソースを取得する
		Document docResourceXml = XmlUtil.getXmlDocument(pathResourceXml);

		// XMLの解析開始
		Element rootElement = docResourceXml.getDocumentElement();

		if (TAG_NAME_ROOT.equals(rootElement.getTagName())) {

			NodeList children = rootElement.getElementsByTagName(TAG_NAME_CHILD);
			for (int i = 0 ; i < children.getLength() ; i++) {

				Element chElement = (Element)children.item(i);

				Attr attrId = chElement.getAttributeNode(ATTR_NAME_CHILD_ID);
				id = attrId.getValue();

				Attr attrPath = chElement.getAttributeNode(ATTR_NAME_CHILD_PATH);
				if (attrPath == null) {
					path = chElement.getTextContent().trim();
				} else {
					path = attrPath.getValue();
				}

				try {
					loadImage(id, path);
				} catch (IOException e) {
					// エラーメッセージ
					// TODO
					System.err.println("image load failed.(id=" + id + ",path=" + path + ")");
					e.printStackTrace();
				}
			}
		}



	}

	public BufferedImage loadImage(String id, String path) throws IOException {

		BufferedImage bufImg = null;
		URL url = ResourceUtil.class.getClassLoader().getResource(path);
		bufImg = ImageIO.read(url);

		this.loadImages.put(id, bufImg);

		return bufImg;
	}

	public BufferedImage getImage(String id) {

		if (this.loadImages.containsKey(id)) {
			return this.loadImages.get(id);
		}

		return null;
	}
}
