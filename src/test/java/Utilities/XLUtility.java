package Utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Reads and writes Excel (.xlsx) files so tests can be data driven.
 * Layout expected: row 0 = column headers, rows 1..n = one test data record each.
 * All cell values come back as text (so a phone number like 9798935572 stays exactly as typed).
 */
public class XLUtility {

    private final String path;
    private final DataFormatter formatter = new DataFormatter();

    public XLUtility(String path) {
        this.path = path;
    }

    /** Number of data rows (the header row is not counted). */
    public int getRowCount(String sheetName) throws IOException {
        try (Workbook wb = open()) {
            return sheet(wb, sheetName).getLastRowNum();   // last index = number of rows after the header
        }
    }

    /** Number of columns in the given row (0 = header row). */
    public int getCellCount(String sheetName, int rowNum) throws IOException {
        try (Workbook wb = open()) {
            Row row = sheet(wb, sheetName).getRow(rowNum);
            return row == null ? 0 : row.getLastCellNum();
        }
    }

    /** One cell as text. Row 0 is the header row. Empty or missing cells return "". */
    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        try (Workbook wb = open()) {
            Row row = sheet(wb, sheetName).getRow(rowNum);
            if (row == null) {
                return "";
            }
            Cell cell = row.getCell(colNum);
            return cell == null ? "" : formatter.formatCellValue(cell);
        }
    }

    /** Writes text into a cell (creates the row or cell if needed) and saves the file. Useful for storing results. */
    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
        try (Workbook wb = open()) {
            Sheet sheet = sheet(wb, sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(data);
            try (FileOutputStream out = new FileOutputStream(path)) {
                wb.write(out);
            }
        }
    }

    /**
     * Whole sheet as a table of text, without the header row.
     * Shape: [number of data rows][number of columns], which is what a TestNG @DataProvider needs.
     */
    public Object[][] getSheetData(String sheetName) throws IOException {
        try (Workbook wb = open()) {
            Sheet sheet = sheet(wb, sheetName);
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[rows][cols];
            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                for (int c = 0; c < cols; c++) {
                    Cell cell = row == null ? null : row.getCell(c);
                    data[r - 1][c] = cell == null ? "" : formatter.formatCellValue(cell);
                }
            }
            return data;
        }
    }

    private Workbook open() throws IOException {
        try (FileInputStream in = new FileInputStream(path)) {
            return new XSSFWorkbook(in);
        }
    }

    private Sheet sheet(Workbook wb, String sheetName) {
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in " + path);
        }
        return sheet;
    }
}