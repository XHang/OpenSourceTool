package com.cxh.xml;

import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

/**
 * 基本xml用法
 * @author Mr-hang
 *
 */
public class BaseXMLAnalysis {
	
	
	/**
	 * 解析输入流，获取xml文档对象
	 * @param inputStream
	 * @return
	 */
	public static Document  genDocument(InputStream inputStream){
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			return document;
		} catch (Exception e) {
			throw new RuntimeException("解析xml失败",e);
		}
	}
	/**
	 * 解析字符串，获取文档对象
	 * @param xml
	 * @return
	 */
	public static Document  genDocument(String xml){
		try {
			return DocumentHelper.parseText(xml);
		} catch (Exception e) {
			throw new RuntimeException("解析xml失败",e);
		}
	}
}
