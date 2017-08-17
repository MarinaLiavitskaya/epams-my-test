package util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class XLSParser {


    public static List<Map<LinkKey, String>> parse(File file) {
        List<Map<LinkKey, String>> linkList = new ArrayList<>();
        InputStream in = null;
        XSSFWorkbook wb = null;

        try {
            in = new FileInputStream(file);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        it.next();
        while (it.hasNext()) {
            Row row = it.next();
            Map<LinkKey, String> map = new HashMap<>();
            map.put(LinkKey.LONG_LINK, row.getCell(0).getStringCellValue());
            map.put(LinkKey.NEW_LONG_LINK, row.getCell(1).getStringCellValue());
            map.put(LinkKey.SHORT_LINK, row.getCell(2).getStringCellValue());
            map.put(LinkKey.NEW_SHORT_LINK, row.getCell(3).getStringCellValue());
            linkList.add(map);
        }

        return linkList;
    }

}