package com.company.OpsUtilities;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by scott.hoornaert on 12-5-2017.
 */
public class MamQuery {

    private String fileName;

    public MamQuery(String fileName) {
        this.fileName = fileName;
    }

    /* Input: An Excel spreadsheet with a column entitled 'StyleOption_ID'
         * Output: A string representing a query to retrieve images for the Style Options
         */
    public String getQuery () {

        String result;
        String selectField;
        selectField = "img.PKOM_MNAME";
        String tableName;
        tableName = "opasdb.dbo.F_IMGKOMP img";
        String queryStart;
        queryStart = "SELECT " + selectField + " as 'Filename', 'delete immediately' as 'delete flag' FROM " + tableName + " WHERE " + selectField;
        String queryBody = "";

        StringBuilder sb = new StringBuilder();

        //Read the excel spreadsheet
        try {
            File temp = new File(fileName);
            FileInputStream file = new FileInputStream(temp);

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet  sheet = workbook.getSheetAt(2);

            Iterator<Row> rowIterator = sheet.iterator();

            int styleColumn = 3;

            int count = 0;
            sb.append(queryBody);

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                String wildcard = "%";

                if (count == 2){
                    String curr = row.getCell(styleColumn).toString();
                    sb.append(" LIKE ('");
                    sb.append(curr);
                    sb.append("%')");

                }

                if (count > 2) {
                    String curr = row.getCell(3).toString();
                    sb.append(" OR " + selectField + " LIKE ('");
                    sb.append(curr);
                    sb.append("%')");
                }
                count++;
            }
            result = queryStart + sb.toString() + ";";
            workbook.close();
        }
        catch (Exception e) {
            result = "File not found.";
        }

        return result;
    }
}
