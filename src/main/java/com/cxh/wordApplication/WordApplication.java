package com.cxh.wordApplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordApplication {
	
	public static  void createWordFile() throws Exception{
		XWPFDocument dox = new XWPFDocument();
		dox.createStyles();
		XWPFTable table = dox.createTable();
		XWPFTableRow row= table.createRow();
		XWPFTableCell cell =row.addNewTableCell();
		cell.setText("我就是天才");
		XWPFTableRow row1= table.createRow();
		XWPFTableCell cell5 =row.addNewTableCell();
		cell.setText("我就是天才");
		XWPFTableRow row2= table.createRow();
		XWPFTableCell cell3 =row.addNewTableCell();
		cell.setText("我就是天才");
		XWPFTableRow row3= table.createRow();
		XWPFTableCell cell4 =row.addNewTableCell();
		cell.setText("我就是天才");
		OutputStream os = new FileOutputStream("D://1.doxc");
		dox.write(os);
		os.close();
	}
}
