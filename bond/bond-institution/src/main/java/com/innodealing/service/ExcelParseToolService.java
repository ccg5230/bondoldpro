package com.innodealing.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.innodealing.model.ExcelColumn;

@Service
public class ExcelParseToolService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelParseToolService.class);

	private static final String SUFFIX_2003 = ".xls";
	private static final String SUFFIX_2007 = ".xlsx";

	private String filePath;
	private List<Method> method;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Workbook initWorkBook() throws IOException {
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);

		Workbook workbook = null;
		if (filePath.endsWith(SUFFIX_2003)) {
			workbook = new HSSFWorkbook(is);
		} else if (filePath.endsWith(SUFFIX_2007)) {
			workbook = new XSSFWorkbook(is);
		}

		return workbook;
	}

	public Workbook initWorkBook(String fileName, InputStream is) throws IOException {
		Workbook workbook = null;
		if (fileName.endsWith(SUFFIX_2003)) {
			workbook = new HSSFWorkbook(is);
		} else if (fileName.endsWith(SUFFIX_2007)) {
			workbook = new XSSFWorkbook(is);
		}

		return workbook;
	}

	public void parseWorkbook(Workbook workbook, List<ExcelColumn> columnList) {
		int numOfSheet = workbook.getNumberOfSheets();
		for (int i = 0; i < numOfSheet; ++i) {
			Sheet sheet = workbook.getSheetAt(i);
			parseSheet(sheet, columnList);
		}
	}

	private void parseSheet(Sheet sheet, List<ExcelColumn> columnList) {
		Row row;
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			row = iterator.next();
			parseRowAndFillDataWithoutFirstRow(columnList, row);
		}
	}

	private void parseRowAndFillDataWithoutFirstRow(List<ExcelColumn> columnList, Row row) {
		int rowNum = row.getRowNum();
		if (rowNum > 0) {
			findDefinedMethod(row);
			parseRowAndFillData(row, columnList);
		} else {
			LOGGER.info("parseRowAndFillDataWithoutFirstRow,rowNum:" + rowNum);
		}
	}

	private void parseRowAndFillData(Row row, List<ExcelColumn> columnList) {
		List<String> rst = parseRow(row);
		ExcelColumn excelColumn = new ExcelColumn();
		if (method.size() != rst.size()) {
			LOGGER.info("ParseRowAndFillData,WTF, size not right.");
		} else {
			try {
				for (int i = 0; i < method.size(); ++i) {
					method.get(i).invoke(excelColumn, rst.get(i));
				}
				columnList.add(excelColumn);
			} catch (Exception e) {
				LOGGER.error("parseRowAndFillData error:" + e.getMessage(), e);
			}
		}

	}

	@Deprecated
	private void parseRowAndFindMethod(Row row) {
		List<String> rst = parseRow(row);
		String methodName;
		try {
			for (String str : rst) {
				methodName = "set" + str;
				method.add(ExcelColumn.class.getDeclaredMethod(methodName, String.class));
			}
		} catch (NoSuchMethodException e) {
			LOGGER.error("parseRowAndFindMethod error:" + e.getMessage(), e);
		}
	}

	private void findDefinedMethod(Row row) {
		String methodName;
		method = new ArrayList<>();
		try {
			Iterator<Cell> iterator = row.iterator();
			while (iterator.hasNext()) {
				Cell cell = iterator.next();
				int columnIndex = cell.getColumnIndex()+1;
				methodName = "set" + "Column" + columnIndex;
				method.add(ExcelColumn.class.getDeclaredMethod(methodName, String.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("findDefinedMethod error:" + e.getMessage(), e);
		}
	}

	private List<String> parseRow(Row row) {
		Cell cell;
		List<String> rst = new ArrayList<>();

		Iterator<Cell> iterator = row.iterator();
		while (iterator.hasNext()) {
			cell = iterator.next();
			cell.setCellType(Cell.CELL_TYPE_STRING);
			rst.add(cell.getStringCellValue());
		}
		return rst;
	}

}
