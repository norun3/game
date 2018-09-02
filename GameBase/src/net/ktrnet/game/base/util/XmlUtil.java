package net.ktrnet.game.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlUtil {

	public static URL findXml(String xmlFileName) {

		try {
			Enumeration<URL> resources = XmlUtil.class.getClassLoader().getResources("");

			while(resources.hasMoreElements()) {

				URL url = resources.nextElement();

				if (xmlFileName.equals(url.getFile())) {
					return url;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
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
}
