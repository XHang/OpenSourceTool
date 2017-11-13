package com.cxh.wordApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class WordApplication {
	
	public static  void XMLToWord(String xml,String wordPath) throws Exception{
		Document doc = DocumentHelper.parseText(xml);
		XMLToWord(doc,wordPath);
	}
	
	@SuppressWarnings("unchecked")
	private static  void XMLToWord(Document xml,String wordPath) throws IOException{
		XWPFDocument word = new XWPFDocument();
		XWPFTable table= word.createTable();
		
		//可以遍历XXX标签下面的所有标签
		Element prplcomponentNode = (Element) xml.selectSingleNode("//XXX");
		Iterator<Element> prplcomponents =   prplcomponentNode.elementIterator();
		int i=0;
		while(prplcomponents.hasNext()){
			Node node = prplcomponents.next();
			XWPFTableRow row = table.createRow();
			row.getCell(0).setText((++i)+"");
			row.createCell().setText(node.getStringValue());
			row.createCell().setText(node.getName());
			row.createCell().setText("String");
			row.createCell().setText("N");
			row.createCell().setText("N");
			row.createCell().setText("               ");
		}
		table.removeRow(0);
		table.setCellMargins(50, 50, 50, 50);
		OutputStream stream = new FileOutputStream(wordPath);
		word.write(stream);
		word.close();
	}
	
	public static  void XMLToWord(InputStream in,String wordPath) throws Exception{
		SAXReader reader = new SAXReader();
		Document doc= 	reader.read(in);
		XMLToWord(doc,wordPath);
	}
}
