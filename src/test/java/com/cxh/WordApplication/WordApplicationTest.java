package com.cxh.WordApplication;

import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.Test;

public class WordApplicationTest {
	
	/**
	 * 目标：创建一个word文档，往里面写一个表格，设计成4行一列
	 * @throws Exception
	 */
	@Test
	public void createTable() throws Exception{
		XWPFDocument doc = new XWPFDocument();
		XWPFTable table = doc.createTable();
		table.getRow(0).getCell(0).setText("表格细胞1");
		//以下创立3行，每一行有1列
		for( int i=2;i<5;i++){
			table.createRow().getCell(0).setText("表格细胞"+i);
		}
		
		
		OutputStream stream = new FileOutputStream("D:\\1.docx");
		doc.write(stream);
		stream.close();
		doc.close();
	}
	
	@Test
	public void test(){
		XWPFDocument doc = new XWPFDocument();
	}
}
