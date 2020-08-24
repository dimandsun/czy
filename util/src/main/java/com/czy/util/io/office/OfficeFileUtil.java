package com.czy.util.io.office;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.czy.util.exception.ParamException;
import com.czy.util.io.FileUtil;
import com.czy.util.list.Iterables;
import com.czy.util.list.ListUtil;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

/**
 * @author chenzy
 * @date 2020-08-24
 * 读写excel、word文档
 */
public class OfficeFileUtil {
    private OfficeFileUtil() {
    }

    public static List<List<StringMap<Object>>> readExcel(File file) {
        return readExcel(file, 0);
    }

    private static Workbook getWorkbook(File file) throws IOException {
        try {
            return new XSSFWorkbook(new FileInputStream(file));
        } catch (OLE2NotOfficeXmlFileException e) {
            return new HSSFWorkbook(new FileInputStream(file));
        }
    }

    /**
     * 读取多个工作簿
     * 参数二是表头索引，默认0
     * @param file
     * @return
     */
    public static List<List<StringMap<Object>>> readExcel(File file, int headIndex) {
        if (!file.exists()) {
            return null;
        }
        try (var workbook = getWorkbook(file)){
            var result = new ArrayList<List<StringMap<Object>>>(workbook.getNumberOfSheets());
            workbook.forEach(sheet -> {
                var data = new ArrayList<StringMap<Object>>();
                var head = new ArrayList<String>();
                Iterables.forEach(sheet, (rowIndex, row) -> {
                    var rowData = new StringMap<>();
                    /*按行读*/
                    Iterables.forEach(row, (colIndex, cell) -> {
                        /*读取头*/
                        if (rowIndex <= headIndex) {
                            var key = getValue(cell, "", String.class);
                            head.add(colIndex, head.contains(key) ? key + colIndex : key);
                            return;
                        }
                        var value = getValue(cell, null, Object.class);
                        rowData.put(head.get(colIndex), value);
                    });
                    if (rowIndex > headIndex) {
                        data.add(rowData);
                    }
                });
                result.add(data);
            });
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static <T> T getValue(Cell cell, T defaultValue, Class<T> tClass) {
        var result = switch (cell.getCellType()) {
            case _NONE, BLANK, ERROR -> defaultValue;
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> cell.getStringCellValue();
            case FORMULA -> cell.getCellFormula();
            case BOOLEAN -> cell.getBooleanCellValue();
        };
        if (result == null) {
            return null;
        }
        return tClass.cast(result);
    }

    public static String readWord(File file){
        if (!file.exists()) {
            return null;
        }
        try {
            var wordExtractor=new XWPFWordExtractor(POIXMLDocument.openPackage(file.getPath()));
            return wordExtractor.getText();
        } catch (OLE2NotOfficeXmlFileException e){
            try {
                var wordExtractor=new WordExtractor(new FileInputStream(file));
                return wordExtractor.getText();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }catch (XmlException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * excel文件写入多个工作簿
     *
     * @param file
     * @param dataMap
     * @param headMap
     */
    public static void writeExcel(File file, Map<String, List<List<Object>>> dataMap, Map<String, List<String>> headMap) {
        /*不存在文件则创建*/
        FileUtil.createFile(file);
        if (dataMap == null || dataMap.isEmpty() || headMap == null || headMap.isEmpty()) {
            return;
        }
        try (var workbook = new XSSFWorkbook()) {
            dataMap.forEach((sheetName, data) -> {
                try {
                    if (StringUtil.isBlank(sheetName)) {
                        throw new ParamException("工作簿名不能为空");
                    }
                    var head = headMap.get(sheetName);
                    setWorkBook(workbook, sheetName, head, data);
                } catch (ParamException e) {
                    e.printStackTrace();
                }
            });
            workbook.write(new FileOutputStream(file));
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void setWorkBook(Workbook workbook, String sheetName, List<String> head, List<List<Object>> data) throws ParamException {
        if (ListUtil.isEmpty(head) || ListUtil.isEmpty(data)) {
            throw new ParamException("表头/数据不能为空");
        }
        /*创建工作簿*/
        var sheet = workbook.createSheet(sheetName);

        /*存放每列宽度*/
        var lengthList = new ArrayList<Integer>();
        /*创建表头*/
        var headRow = sheet.createRow(0);
        Iterables.forEach(head, (index, value) -> {
            if (value == null) {
                value = "";
            }
            headRow.createCell(index).setCellValue(value);
            lengthList.add(value.getBytes().length);
        });
        Iterables.forEach(data, (rowIndex, list) -> {
            var row = sheet.createRow(rowIndex + 1);
            Iterables.forEach(list, (colIndex, value) -> {
                if (StringUtil.isBlank(value)) {
                    return;
                }
                /*舍去比表头长的列*/
                if (lengthList.size() <= colIndex) {
                    return;
                }
                /*长度设值*/
                var tempLength = value.toString().getBytes().length;
                if (lengthList.get(colIndex).compareTo(tempLength) < 0) {
                    lengthList.set(colIndex, tempLength);
                }
                /*写入列数据*/
                var cell = row.createCell(colIndex);
                if (value instanceof String v) {
                    cell.setCellValue(v);
                } else if (value instanceof Number v) {
                    cell.setCellValue(v.doubleValue());
                } else if (value instanceof Date v) {
                    cell.setCellValue(v);
                } else if (value instanceof LocalDateTime v) {
                    cell.setCellValue(v);
                } else if (value instanceof LocalDate v) {
                    cell.setCellValue(v);
                } else if (value instanceof Calendar v) {
                    cell.setCellValue(v);
                } else if (value instanceof Boolean v) {
                    cell.setCellValue(v);
                } else if (value instanceof RichTextString v) {
                    cell.setCellValue(v);
                } else {
                    cell.setCellValue(value.toString());
                }
            });
        });
        /*设置列长度*/
        Iterables.forEach(lengthList, (index, size) -> {
            sheet.setColumnWidth(index, size * 256);
        });
    }

    /**
     * excel文件中写入数据:写入一个工作簿
     *
     * @param file
     * @param data      一行行数据
     * @param sheetName 工作簿名
     * @return
     */
    public static void writeExcel(File file, List<List<Object>> data, List<String> head, String sheetName) {
        /*不存在文件则创建*/
        FileUtil.createFile(file);
        if (data == null || data.isEmpty() || head == null || head.isEmpty()) {
            return;
        }
        try (var workbook = new XSSFWorkbook()) {
            setWorkBook(workbook, sheetName, head, data);
            workbook.write(new FileOutputStream(file));
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ParamException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //创建工作簿 类似于创建Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建 sheetname页名
        XSSFSheet sheet = workbook.createSheet("员工信息");
        //创建一行,下标从0开始
        XSSFRow row = sheet.createRow(0);
        //创建这行中的列,下标从0开始 （表头）
        XSSFCell cell = row.createCell(0);
        // 给cell 0下表赋值
        cell.setCellValue("姓名");
        //创建这行中的列,并给该列直接赋值
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("生日");
        row.createCell(4).setCellValue("手机号");
        // 设置表里内容
        row = sheet.createRow(1);
        row.createCell(0).setCellValue("T");
        row.createCell(1).setCellValue("保密");
        row.createCell(2).setCellValue("男");
        row.createCell(3).setCellValue("保密");
        row.createCell(4).setCellValue("12121212121");

        row = sheet.createRow(2);
        row.createCell(0).setCellValue("T");
        row.createCell(1).setCellValue("18");
        row.createCell(2).setCellValue("女");
        row.createCell(3).setCellValue("2000-01-01");
        row.createCell(4).setCellValue("12121212122");


        sheet.setColumnWidth(3, 2 * 256);//给第4列设置为20个字的宽度
        sheet.setColumnWidth(4, 20 * 256);//给第5列设置为20个字的宽度

        File file = new File("/czy/file/test.xlsx");
        FileUtil.createFile(file);
        //设定 路径
        FileOutputStream stream = new FileOutputStream(file);
        // 需要抛异常
        workbook.write(stream);
        //关流
        stream.close();
    }
}
