package com.cxh;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.formula.functions.Columns;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.helpers.ColumnHelper;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

public class ExcelApplication {

    public static void main(String [] args) throws Exception{
        Workbook wb = new XSSFWorkbook();
        //工作簿的名字不能超过31字符，不得使用特殊字符
        //为了防止翻车，可以使用WorkbookUtil.createSafeSheetName来生成合适的名字
        Sheet sheet = wb.createSheet("报装统计报表");

        CreationHelper createHelper = wb.getCreationHelper();
        //创建一行并且放入一个单元格
        Row row = sheet.createRow(0);

        //在行上创建一个单元格，并设置值1
        Cell cell = row.createCell(0);
        cell.setCellValue(1);

        row.createCell(2).setCellValue(
                createHelper.createRichTextString("This is a string"));
        row.createCell(3).setCellValue(true);

        //创建一个日期格式的单元格
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy/m/d"));
        cell = row.createCell(4);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(5);
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        try (OutputStream fileOut = new FileOutputStream("D://大亚湾开玩笑.xlsx")) {
            wb.write(fileOut);
        }


    }


}
