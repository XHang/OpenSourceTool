package com.cxh.qdoxExample;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import javax.swing.plaf.synth.SynthSeparatorUI;
import org.junit.Test;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.ClassLibrary;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

public class JavaFileAnalysis {
	
	@Test
	public void test() throws IOException{
		JavaDocBuilder builder = new JavaDocBuilder();
		InputStreamReader reader = new InputStreamReader(this.getClass().getResource("/JavaBean.java").openStream());
		JavaSource source =   builder.addSource(reader);
		JavaClass clazz = source.getClasses()[0];
		
		System.out.println("获取类名,不带全称"+clazz.getName());
		
		System.out.println("获取全类名"+clazz.getFullyQualifiedName());
		
		System.out.println("获取包名"+clazz.getPackage());
		
		//获取类的注释有几点
		//1:只有@注解上面的注释会被提取
		//2:   //注释不可生效
		System.out.println("类上面的注释是"+clazz.getComment());
		
		
		System.out.println("获取类注释上面的author注解值"+clazz.getTagByName("author").getValue());

		
	}
}
