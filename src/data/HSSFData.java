package data;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HSSFData {

    private String fileName;
    private HSSFWorkbook workbook;
    private final Map<String, HSSFCellStyle> map = new HashMap<>();

    public Map<String, HSSFCellStyle> getMap() {
        return map;
    }

    public void writeHSSFJournal(ArrayList<ArrayList<Mark>> matrix) {
        HSSFSheet sheet = workbook.getSheet("3 четверть"); ///
        for (int i = 0; i < matrix.size(); i++) {
            HSSFRow row = sheet.getRow(i+1);
            for (int j = 0; j < matrix.get(0).size(); j++) {
                HSSFCell cell = row.getCell(j+2);
                Mark mark = matrix.get(i).get(j);
                cell.setCellStyle(map.get(mark.toStyle()));
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

    private void writeWorkbook() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (Exception e) {
            System.out.println("Ошибка в writeWorkbook");
        }
    }

    public ArrayList<ArrayList<Mark>> readHSSFJournal(String filename) {
        this.fileName = filename;
        workbook = readWorkbook(fileName);
        assert workbook != null;
        readStyle();
        ArrayList<ArrayList<Mark>> matrix = new ArrayList<>();
        HSSFSheet sheet = workbook.getSheet("3 четверть"); ///
        int countCell = findSizeRowSheet(sheet);
        int countRow = findSizeColumnSheet(sheet);
        for (int i = 1; i < countRow; i++) {
            HSSFRow row = sheet.getRow(i);
            matrix.add(readHSSFRow(row,countCell));
        }
        return matrix;
    }

    private void readStyle() {
        HSSFSheet styleSheet = workbook.getSheet("Стили");
        for (int i = 1; i <= 25; i++) {
            HSSFRow styleRow = styleSheet.getRow(i);
            HSSFCell nameCell = styleRow.getCell(0);
            HSSFCell styleCell = styleRow.getCell(1);
            map.put(nameCell.getStringCellValue(),styleCell.getCellStyle());
        }
    }

    private HSSFWorkbook readWorkbook(String filename) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
            return new HSSFWorkbook(fs);
        }
        catch (Exception e) {
            System.out.println("Ошибка в readWorkbook");
            return null;
        }
    }

    public int findSizeRowSheet(HSSFSheet sheet) {
        //13 потому что оценки 13 не может быть
        HSSFRow row = sheet.getRow(13);
        int countCell = 2;
        Mark mark;
        do {
            HSSFCell cell = row.getCell(countCell);
            mark = readCell(cell);
            countCell++;
        } while (mark.isSetBoolean() && countCell < 70);
        if (countCell != 60)
            return countCell-3;
        return 0;
    }

    public int findSizeColumnSheet(HSSFSheet sheet) {
        int countRow = 1;
        Mark mark;
        do {
            HSSFRow row = sheet.getRow(countRow);
            HSSFCell cell = row.getCell(0);
            mark = readCell(cell);
            countRow++;
        } while (!mark.toStyle().equals("cell") && countRow < 27);
        if (countRow != 27)
            return countRow-1;
        return 0;
    }

    public ArrayList<Mark> readHSSFRow(HSSFRow row, int countCell) {
        ArrayList<Mark> arr = new ArrayList<>();
        for (int i = 2; i < countCell+2; i++) {
            HSSFCell cell = row.getCell(i);
            arr.add(readCell(cell));
        }
        return arr;
    }

    public Mark readCell(HSSFCell cell) {
//        if (cell != null) {
//            System.out.println(cell.getCellType() + "_" + cell.getRowIndex() + ":" + cell.getColumnIndex());
//        } else {
//            System.out.println("null");
//        }

        Mark mark = new Mark();

        if (cell == null)
            return mark;

        mark.setStyle(cell.getCellStyle().getParentStyle().getUserStyleName());
        if (cell.getCellType() == CellType.BLANK) {
            return mark;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            mark.set((int)cell.getNumericCellValue());
            return mark;
        }
        if (cell.getCellType() == CellType.STRING) {
            mark.setString(cell.getStringCellValue());
            return mark;
        }
        mark.setString("?");
        return mark;
    }
}
