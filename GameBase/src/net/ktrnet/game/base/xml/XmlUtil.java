package net.ktrnet.game.base.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.ktrnet.game.base.util.TypeUtil;

public class XmlUtil {

	public static URL findXml(String xmlFileName) {

		try {
			Enumeration<URL> resources = XmlUtil.class.getClassLoader().getResources("");

			URL retUrl = null;
			while(resources.hasMoreElements()) {

				URL url = resources.nextElement();
				System.out.println(url.toString());

				File f = Paths.get(url.toURI()).toFile();

				retUrl = null;
				if (f.isFile() && f.getName().equals(xmlFileName)) {
					retUrl = url;
				} else if (f.isDirectory()) {
					retUrl = findXml(f, xmlFileName);
				}

				if (retUrl != null) {
					return retUrl;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static URL findXml(File dir, String xmlFileName) throws MalformedURLException {

		URL url = null;

		for (File f : dir.listFiles()) {

			System.out.println(f.toString());

			url = null;
			if (f.isFile() && f.getName().equals(xmlFileName)) {

				url = f.toPath().toUri().toURL();

			} else if (f.isDirectory()) {
				url = findXml(f, xmlFileName);
			}

			if (url != null) {
				return url;
			}
		}

		return null;
	}

	public static Document getXmlDocument(URL url) {

		Document document = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.parse(new FileInputStream(new File(url.toURI())));

		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return document;
	}

	public static List<Element> findElement(Element e, String tags) {

		String[] findTagList = tags.split("\\.", 2);

		List<Element> findedList = new ArrayList<Element>();

		NodeList children = e.getElementsByTagName(findTagList[0]);
		for (int i = 0 ; i < children.getLength() ; i++) {
			if (findTagList.length > 1) {
				findedList.addAll(
						findElement((Element)children.item(i), findTagList[1]));
			} else {
				findedList.add((Element)children.item(i));
			}
		}

		return findedList;
	}

	public static Element getElement(Element e, String tags) {
		List<Element> findedList = findElement(e, tags);
		if (findedList.size() == 0) {
			return null;
		}

		return findedList.get(0);
	}

	public static String getElementValue(Element e, String tags) {

		List<Element> findedList = findElement(e, tags);
		if (findedList.size() == 0) {
			return null;
		}

		String eValue = null;

		Element finded = findedList.get(0);
		if (finded.hasAttribute("value")) {
			eValue = finded.getAttribute("value");
		} else {
			eValue = finded.getTextContent();
		}

		return eValue;
	}

	public static Integer getIntValue(Element e, String attr) {

		return TypeUtil.parseInteger(e.getAttribute(attr));
	}

	public static String getStrValue(Element e, String attr) {
		return e.getAttribute(attr);
	}
}
