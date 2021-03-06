package reportV460.Helpers;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class StyleDocument {

    public static CellStyle createBaseStyle(HSSFWorkbook book) {
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 8);

        CellStyle style = book.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        return style;
    }

    public static CellStyle createHeadingStyle(HSSFWorkbook book) {
        Font headerFont = book.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 8);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = book.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setFont(headerFont);

        return headerCellStyle;
    }

    public static String setFont(){
        return HSSFHeader.font("Arial", "regular");
    }

    public static String setBold(String text){
        return HSSFHeader.startBold() + text + HSSFHeader.endBold();
    }





}
