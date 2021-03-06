package backend;

import backend.marks.Cell;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HSSFData {

    private static String fileName;
    private static HSSFWorkbook workbook;
    private static final Map<String, HSSFCellStyle> styleMap = new HashMap<>();

    public static Map<String, HSSFCellStyle> getStyleMap() {
        return styleMap;
    }

    public static String getFileName() {
        return fileName.substring(fileName.lastIndexOf("\\")+1,fileName.indexOf(".xls"));
    }

    public static void writeHSSFJournal(ArrayList<ArrayList<Cell>> matrix) {
        HSSFSheet sheet = workbook.getSheet("Оценки"); ///
        for (int i = 0; i < matrix.size(); i++) {
            HSSFRow row = sheet.getRow(i+1);
            for (int j = 0; j < matrix.get(0).size(); j++) {
                HSSFCell cell = row.getCell(j+2);
                Cell mark = matrix.get(i).get(j);
//                System.out.println(mark.toStyle() + " " + mark.getRow() + " " + mark.getCol());
                cell.setCellStyle(styleMap.get(mark.toStyle()));
                if (mark.get()==0) {
                    cell.setCellValue(mark.toString());
                }
                else {
                    cell.setCellValue(mark.get());
                }
            }
        }
        writeWorkbook();
    }

    private static void writeWorkbook() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (Exception e) {
            System.out.println("Ошибка в writeWorkbook");
        }
    }

    public static ArrayList<ArrayList<Cell>> readHSSFJournal(String file) {
        fileName = file;
        workbook = readWorkbook(fileName);
        assert workbook != null;
        readStyle();
        ArrayList<ArrayList<Cell>> matrix = new ArrayList<>();
        HSSFSheet sheet = workbook.getSheet("Оценки"); ///
        int countCell = findColumnCount(sheet);
        int countRow = findRowCount(sheet);
        for (int i = 1; i < countRow; i++) {
            HSSFRow row = sheet.getRow(i);
            matrix.add(readHSSFRow(row,i-1,countCell));
        }
        return matrix;
    }

    private static void readStyle() {
        HSSFSheet styleSheet = workbook.getSheet("Стили");
        for (int i = 1; i <= 21; i++) {
            HSSFRow styleRow = styleSheet.getRow(i);
            HSSFCell nameCell = styleRow.getCell(0);
            HSSFCell styleCell = styleRow.getCell(1);
            styleMap.put(nameCell.getStringCellValue(),styleCell.getCellStyle());
        }
    }

    private static HSSFWorkbook readWorkbook(String filename) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
            return new HSSFWorkbook(fs);
        }
        catch (Exception e) {
            System.out.println("Ошибка в readWorkbook");
            return null;
        }
    }

    public static int findColumnCount(HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(0);
        int countCell = 0;
        HSSFCell cell;
        do {
            cell = row.getCell(countCell);
            countCell++;
        } while (cell.getCellType() != CellType.BLANK);
        return countCell-5;
    }

    public static int findRowCount(HSSFSheet sheet) {
        int countRow = 0;
        HSSFCell cell;
        do {
            HSSFRow row = sheet.getRow(countRow);
            cell = row.getCell(0);
            countRow++;
        } while (cell.getCellType() != CellType.BLANK);
        return countRow-2;
    }

    public static ArrayList<Cell> readHSSFRow(HSSFRow row, int rowNumber, int countCell) {
        ArrayList<Cell> arr = new ArrayList<>();
        for (int i = 2; i < countCell+2; i++) {
            HSSFCell cell = row.getCell(i);
            arr.add(readCell(cell, rowNumber, i-2));
        }
        return arr;
    }

    public static Cell readCell(HSSFCell cell, int row, int col) {
        Cell mark = new Cell(row, col);

        if (cell == null)
            return mark;

        mark.setStyle(cell.getCellStyle().getParentStyle().getUserStyleName());

        if (cell.getCellType() == CellType.NUMERIC) {
            mark.set((int)cell.getNumericCellValue());
            return mark;
        }
        return mark;
    }
}
