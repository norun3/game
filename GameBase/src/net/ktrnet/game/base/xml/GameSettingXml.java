package net.ktrnet.game.base.xml;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.ktrnet.game.base.GFrame;
import net.ktrnet.game.base.exception.GameXmlException;
import net.ktrnet.game.base.logic.GameLogic;
import net.ktrnet.game.base.logic.GameWorldLogic;
import net.ktrnet.game.base.object.GObject;
import net.ktrnet.game.base.object.GScene;
import net.ktrnet.game.base.util.ResourceUtil;
import net.ktrnet.game.base.util.TypeUtil;
import net.ktrnet.game.base.visual.GBackGround;
import net.ktrnet.game.base.visual.GFrontGround;
import net.ktrnet.game.base.visual.GMainGround;
import net.ktrnet.game.base.visual.GObjectList;

public class GameSettingXml {

	public static GFrame loadGFrame() throws GameXmlException {

		GFrame frame = new GFrame();

		// 設定XMLファイルの検索
		URL pathResourceXml = XmlUtil.findXml("GameSetting.xml");
		if (pathResourceXml == null) {
			throw new GameXmlException("GameSetting.xml", null, null,
					"can not find.");
		}

		// XMLを読み込みリソースを取得する
		Document doc = XmlUtil.getXmlDocument(pathResourceXml);

		// XMLの解析開始
		Element root = doc.getDocumentElement();

		if (!"GameSetting".equals(root.getTagName())) {
			throw new GameXmlException("GameSetting.xml", null, null,
					"root Element is not 'GameSetting'.");
		}

		// windowタグ
		String initSceneId = null;
		Element eWindow = XmlUtil.getElement(root, "window");
		if (eWindow != null) {
			String title = eWindow.getAttribute("title");
			if (title != null) {
				frame.setTitle(title);
			}

			Integer intWinWidth = TypeUtil.parseInteger(eWindow.getAttribute("width"));
			Integer intWinHeight = TypeUtil.parseInteger(eWindow.getAttribute("height"));
			if (intWinWidth != null && intWinHeight != null) {
				frame.setSize(intWinWidth, intWinHeight);
				frame.setLocationRelativeTo(null);
			}

			initSceneId = eWindow.getAttribute("sceneId");
		}

		// Scenesタグ

		// ID管理用
		Map<String, GObject> gobjCheckList = new HashMap<String, GObject>();

		String firstSceneId = null;
		List<Element> scenes = XmlUtil.findElement(root, "Scenes.Scene");
		for (Element scene : scenes) {
			GScene gscene = new GScene();
			gscene.setId(scene.getAttribute("id"));

			// １つ目のシーンIDを取得（初期シーンIDが指定されていない場合に参照）
			if (firstSceneId == null) {
				firstSceneId = gscene.getId();
			}

			Element eBackGround = XmlUtil.getElement(scene, "BackGround");
			if (eBackGround != null) {
				GBackGround backGround = new GBackGround();

				// 属性値を取得
				String imagePath = eBackGround.getAttribute("image");
				String colorName = eBackGround.getAttribute("color");
				Integer colorRValue = TypeUtil.parseInteger(eBackGround.getAttribute("red"));
				Integer colorGValue = TypeUtil.parseInteger(eBackGround.getAttribute("green"));
				Integer colorBValue = TypeUtil.parseInteger(eBackGround.getAttribute("blue"));

				// 背景イメージが指定されている場合
				if (imagePath != null) {

					try {
						Image image = ResourceUtil.getImage(imagePath);
						backGround.setImage(image);
					} catch (IOException e) {
						// 指定されたイメージがない場合、エラー
						throw new GameXmlException("GameSetting.xml", eBackGround, "image",
								"image resource is not exist.(" + imagePath + ")");
					}

				// 背景イメージが指定されていない、背景色名が指定されている場合
				} else if (colorName != null) {

					// 背景色名で設定
					backGround.setColor(Color.getColor(colorName));

				// 背景イメージ、背景色名が指定されていない時
				// 背景色（三値）が指定されている場合
				} else if (colorRValue != null && colorGValue != null && colorBValue != null) {

					// 背景色値で設定
					backGround.setColor(new Color(colorRValue, colorGValue, colorBValue));

				// 上記のどれも指定されていない場合、エラー
				} else {
					throw new GameXmlException("GameSetting.xml", eBackGround, null,
							"BackGround is not specified. define the image or color.");
				}

				gscene.setBackGround(backGround);
				gobjCheckList.put(backGround.getId(), backGround);
			}

			Element eFrontGround = XmlUtil.getElement(scene, "FrontGround");
			if (eFrontGround != null) {
				GFrontGround frontGround = new GFrontGround();
				GObjectList gobjects = loadGameObjects(eFrontGround, gobjCheckList);
				frontGround = new GFrontGround();
				frontGround.addAll(gobjects);
				gscene.setFrontGround(frontGround);
			}

			Element eMainGround = XmlUtil.getElement(scene, "MainGround");
			if (eMainGround != null) {
				GMainGround mainGround = new GMainGround();
				GObjectList gobjects = loadGameObjects(eMainGround, gobjCheckList);
				mainGround = new GMainGround();
				mainGround.addAll(gobjects);

				Element eGameWorldLogic = XmlUtil.getElement(eMainGround, "GameWorldLogic");
				try {
					GameWorldLogic gameWorldLogic = (GameWorldLogic)loadClassInstance(eGameWorldLogic);
					mainGround.setGameWorldLogic(gameWorldLogic);
				} catch (GameXmlException e) {
					// 定義なしの場合、設定しない
				}

				gscene.setMainGround(mainGround);
			}

			Element eGameLogic = XmlUtil.getElement(scene, "GameLogic");
			if (eGameLogic != null) {
				GameLogic gameLogic = (GameLogic)loadClassInstance(eGameLogic);

				gscene.setGameLogic(gameLogic);
			}

			String focusObject = scene.getAttribute("focusObject");
			if (focusObject != null) {
				gscene.setFocusObject(focusObject);
			}

			frame.addScene(gscene);
		}

		// 初期シーン指定時
		if (initSceneId != null && frame.getScenes().containsKey(initSceneId)) {

			// 初期シーンを設定
			frame.setScene(initSceneId);

		// 初期シーン未指定時
		} else if (firstSceneId != null && frame.getScenes().containsKey(firstSceneId)) {

			// １つ目シーンを設定
			frame.setScene(firstSceneId);

		// 上記以外はエラー
		} else {
			throw new GameXmlException("GameSetting.xml", null, null,
					"Init Scene is not defined. please define a initSceneId or Scene.");
		}

		return frame;
	}


	private static GObjectList loadGameObjects(Element eParent, Map<String, GObject> checkList) throws GameXmlException {

		GObjectList objects = new GObjectList();

		List<Element> eGameObjects = XmlUtil.findElement(eParent, "GameObjects.GameObject");

		for (Element eGameObject : eGameObjects) {

			GObject gobj = loadGameObject(eGameObject, checkList);

			objects.add(gobj);
		}

		return objects;
	}

	private static GObject loadGameObject(Element eGameObject, Map<String, GObject> checkList) throws GameXmlException {

		GObject gobj = null;

		// 属性値を取得
		String id = eGameObject.getAttribute("id");
		String name = eGameObject.getAttribute("name");
		String clazz = eGameObject.getAttribute("class");
		String imagePath = eGameObject.getAttribute("image");
		Integer x = TypeUtil.parseInteger(eGameObject.getAttribute("x"));
		Integer y = TypeUtil.parseInteger(eGameObject.getAttribute("y"));
		Integer width = TypeUtil.parseInteger(eGameObject.getAttribute("width"));
		Integer height = TypeUtil.parseInteger(eGameObject.getAttribute("height"));
		Integer repeatw = TypeUtil.parseInteger(eGameObject.getAttribute("repeatw"));
		Integer repeath = TypeUtil.parseInteger(eGameObject.getAttribute("repeath"));

		if (id == null || clazz == null || imagePath == null) {
			StringBuffer sbAttrs = new StringBuffer();
			if (id == null) {
				sbAttrs.append("id ");
			}

			if (clazz == null) {
				sbAttrs.append("class ");
			}

			if (imagePath == null) {
				sbAttrs.append("image ");
			}

			throw new GameXmlException("GameSetting.xml", eGameObject, sbAttrs.toString(),
					"Attribute is not defined.");
		}

		// ID重複確認
		if (checkList.containsKey(id)) {
			throw new GameXmlException("GameSetting.xml", eGameObject, "id",
					"This id(" + id + ") is duplicated.");
		}

		Image image = null;
		try {
			image = ResourceUtil.getImage(imagePath);
		} catch (IOException e) {
			// イメージ取得失敗エラー
			throw new GameXmlException("GameSetting.xml", eGameObject, "image",
					"image path is not exist.(" + imagePath + ")");
		}

		// クラス生成
		Object obj = loadClassInstance(eGameObject);
		if (!(obj instanceof GObject)) {
			// クラスがGObjectを継承していない
			throw new GameXmlException("GameSetting.xml", eGameObject, "class",
					"class is not extends GObject class.(" + clazz + ")");
		}

		if (x == null) {
			x = 0;
		}

		if (y == null) {
			y = 0;
		}

		if (width == null) {
			width = image.getWidth(null);
		}

		if (height == null) {
			height = image.getHeight(null);
		}

		if (repeatw == null) {
			repeatw = 1;
		}

		if (repeath == null) {
			repeath = 1;
		}

		gobj = (GObject)obj;
		gobj.setId(id);
		gobj.setX(x);
		gobj.setY(y);
		gobj.setWidth(width);
		gobj.setHeight(height);
		gobj.setRepeatw(repeatw);
		gobj.setRepeath(repeath);
		gobj.setImage(image);
		if (name != null) {
			gobj.setName(name);
		}

		// チェックリストにも追加
		checkList.put(gobj.getId(), gobj);

		return gobj;
	}

	private static Object loadClassInstance(Element e) throws GameXmlException {

		Object obj = null;

		if (e != null) {
			String className = e.getAttribute("class");
			try {
				Class<?> clazz = Class.forName(className);
				obj = clazz.newInstance();
			} catch (Exception ex) {
				throw new GameXmlException("GameSetting.xml", e, "class",
						"class is not instanced.", ex);
			}
		}

		return obj;
	}
}
