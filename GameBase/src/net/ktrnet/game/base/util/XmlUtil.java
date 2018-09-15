package net.ktrnet.game.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
}
