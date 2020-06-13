package data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class HSSFData {

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

    private void writeWorkbook(HSSFWorkbook wb, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        }
        catch (Exception e) {
            System.out.println("Ошибка в writeWorkbook");
        }
    }

    public ArrayList<ArrayList<Mark>> readHSSFJournal(String filename) {
        HSSFWorkbook workbook = readWorkbook(filename);
        if (workbook == null)
            return null;
        ArrayList<ArrayList<Mark>> matrix = new ArrayList<>();
        HSSFSheet sheet = workbook.getSheet("3 четверть"); //////
        int countCell = findSizeRowSheet(sheet);
        int countRow = findSizeColumnSheet(sheet);
        for (int i = 1; i < countRow; i++) {
            HSSFRow row = sheet.getRow(i);
            matrix.add(readHSSFRow(row,countCell));
        }
        return matrix;
    }

    //                System.out.println(cell.getCellStyle().getFillForegroundColorColor().getHexString() + " " + cellValue);

    public int findSizeRowSheet(HSSFSheet sheet) {
        //13 потому что оценки 13 не может быть
        HSSFRow row = sheet.getRow(13);
        int countCell = 2;
        String cellValue;
        do {
            HSSFCell cell = row.getCell(countCell);
            cellValue = readCell(cell).toString();
            countCell++;
        } while (!cellValue.equals("end") && countCell < 70);
        if (countCell != 60)
            return countCell-3;
        return 0;
    }

    public int findSizeColumnSheet(HSSFSheet sheet) {
        int countRow = 1;
        String cellValue;
        do {
            HSSFRow row = sheet.getRow(countRow);
            HSSFCell cell = row.getCell(0);
            cellValue = readCell(cell).toString();
            countRow++;
        } while (!cellValue.equals("") && countRow < 27);
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
        Mark mark = new Mark();
        if (cell == null) {
            mark.setString("");
            return mark;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (!mark.set((int)cell.getNumericCellValue()))
                mark.setString("end");
            return mark;
        }
        if (cell.getCellType() == CellType.STRING) {
            mark.setString(cell.getStringCellValue());
            return mark;
        }
        mark.setString("(f)");
        return mark;
    }
}
