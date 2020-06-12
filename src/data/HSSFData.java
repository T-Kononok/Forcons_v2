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

    public ArrayList<ArrayList<String>> readHSSFJournal(String filename) {
        HSSFWorkbook workbook = readWorkbook(filename);
        if (workbook != null) {

            ArrayList<ArrayList<String>> matrix = new ArrayList<>();
            HSSFSheet sheet = workbook.getSheet("3 четверть"); //////
            Iterator<Row> rowIter = sheet.rowIterator();
            rowIter.next();
            int countRow = 0;
            while (countRow < 24) {
                countRow++;
                HSSFRow row = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = row.cellIterator();
                ArrayList<String> arr = new ArrayList<>();
                cellIter.next();
                cellIter.next();
                int countCell = 0;
                while (cellIter.hasNext() && countCell < 40) {
                    countCell++;
                    HSSFCell cell = (HSSFCell) cellIter.next();
                    while (arr.size() + 2 < cell.getColumnIndex() && countCell < 40) {
                        countCell++;
                        String str = "";
                        arr.add(str);
                    }
                    String str;
                    if (cell.getCellType() == CellType.NUMERIC)
                        str = (int) cell.getNumericCellValue() + "";
                    else if (cell.getCellType() == CellType.STRING)
                        str = cell.getStringCellValue();
                    else
                        str = "(F)";
                    arr.add(str);
                }
                matrix.add(arr);
            }
            return matrix;

        }
        return null;
    }
}
