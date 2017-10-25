package com.cxh.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 基本xml解析
 * @author Mr-hang
 *
 */
public class BaseXMLUsage {

	/**
	 * xml迭代元素测试
	 * @throws IOException 
	 */
	@Test
	public void iterateElement() throws IOException{
		InputStream in = this.getClass().getResourceAsStream("/example.xml");
		Document document = BaseXMLAnalysis.genDocument(in);
		Element rootElement = document.getRootElement();
		//根据标签名获取根元素下一级符合条件的第一个标签
		Element list = rootElement.element("userlist");
		//遍历该标签下所有user的标签
		for(Iterator<Element> iterator = list.elementIterator("user");iterator.hasNext();){
			Element element =  iterator.next();
			// ‘.//name’代表选择当前元素下面的name标签，如果仅仅只是\\name则不考虑位置，从全部位置取
			printlnXml(".//name",element);
			printlnXml(".//sex",element);
			printlnXml(".//career",element);
			printlnXml(".//nationality",element);
			printlnXml(".//appraisal",element);
		}
		in.close();
	}
	
	private void printlnXml(String xpath,Element element){
		Node node = element.selectSingleNode(xpath);
		System.out.println("我们选取的标签名系"+node.getName());
		System.out.println("我们选取的标签值是"+node.getStringValue());
	}
	
	
	
}
