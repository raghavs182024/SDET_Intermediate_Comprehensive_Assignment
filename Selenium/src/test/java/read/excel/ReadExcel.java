package read.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.testng.annotations.Test;


public class ReadExcel {

    @Test
    public void readExcel() {
        String excelFilePath = "src/main/resources/Emp_Data.xlsx";

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Set<String> printedValues = new HashSet<>();
            System.out.println("+-----------+-----------+--------------------+-----------++-----------+");

            for (Row row : sheet) {
                for (Cell cell : row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    if (printedValues.add(cellValue)) {
                        System.out.printf(" %-10s |", cellValue);
                    }
                }
                System.out.println();
                System.out.println("+-----------+-----------+--------------------+-----------+-----------+");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}