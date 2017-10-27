package com.cxh.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import com.cxh.bean.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
	
	@Test
	public void XMLToObject() throws IOException{
		InputStream in = this.getClass().getResourceAsStream("/example.xml");
		Document document = BaseXMLAnalysis.genDocument(in);
		Node usersNode = document.selectSingleNode("//userlist");
		//xStream默认使用XppDriver解析，总是报错Cannot create XmlPullParser
		//故换成DomDriver
		XStream xStream  = new XStream(new DomDriver());
		//解析成List集合
		xStream.alias("userlist", List.class);
		//把user标签以下的内容解析成User类的内容
		xStream.alias("user", User.class);
		//实例化成user时，把里面xml的appraisal标签转换成user的desc字段
		xStream.aliasField("appraisal", User.class, "desc");
		String xml = usersNode.asXML();
		System.out.println("需要转成对象的xml字符串是"+xml);
		List <User> users = (List<User>) xStream.fromXML(xml);
		for(User user:users){
			System.out.println("now read User");
			System.out.println("The user's name :"+user.getName());
			System.out.println("The user's sex :"+user.getSex());
			System.out.println("The user's nationnality :"+user.getNationality());
			System.out.println("The user's desc :"+user.getDesc());
		}
		ObjectToXml(users);
		in.close();
	}
	
	/**
	 * 将对象转成xml
	 * @param users
	 */
	public void ObjectToXml(List <User> users){
		XStream xStream  = new XStream(new DomDriver());
		//将对象中的desc字段名映射到xml的appraisal标签
		xStream.aliasField("appraisal", User.class, "desc");
		//如果不加这下面的两个的话，生成的xml文件头标签是类名
		xStream.alias("user", User.class);
		xStream.alias("userList", List.class);
		System.out.println("下面将list对象转成对象");
		System.out.println(xStream.toXML(users));
	}
	
	
}
