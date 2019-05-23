package sample.Report;
import java.io.*;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.*;
import sample.Helpers.LogInfo;
import sample.Helpers.StyleDocument;
import sample.Report.Strategy.*;
import sample.v460.DriverType;
import sample.v460.PointParam;



public class ReportCreator {

    public static void CreateDocFile(ArrayList<PointParam> pointParams, String docFile) throws IOException {
        XWPFDocument document = new XWPFDocument();

        for(PointParam point: pointParams){
            ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createDocTable(document, point);
        }
        writeDataToFile(document, docFile);
    }

    public static void CreateXlsFile(ArrayList<PointParam> pointParams, String xlsFile) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook();

        initPropertiesSheetAndHeaderFooter(book);

        for(PointParam point: pointParams){
            ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createXlsTable(book, point);
        }

        addSignaturesToLastPage(book);

        writeDataToFile(book, xlsFile);
    }



    private static void initPropertiesSheetAndHeaderFooter(HSSFWorkbook book){
        book.createSheet("Report");
        Sheet sheet = book.getSheetAt(0);
        sheet.getPrintSetup().setLandscape(true);
        sheet.getPrintSetup().setPaperSize(PrintSetup.A3_PAPERSIZE);
        //sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A5_PAPERSIZE);

        String numPr = "";
        Header header = sheet.getHeader();

        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat munthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("YY");
        String munth = munthFormat.format(calendar.getTime());
        String year = yearFormat.format(calendar.getTime());

        //header.setLeft(dateFormat.format(calendar.getTime()));
        header.setCenter(
                StyleDocument.setBold("Приложение " + numPr +" к протоколу испытаний №"+ numPr +"-"+ munth +"/"+ year)
        );

        Footer footer = sheet.getFooter();
        footer.setCenter(StyleDocument.setBold("Страница " + HeaderFooter.page() + " из " + HeaderFooter.numPages()));
    }

    private static void addSignaturesToLastPage(HSSFWorkbook destWorkBook) {
        HSSFWorkbook templateWorkBook = getTemplateBook();
        try {
            copyRow(templateWorkBook, destWorkBook);
        }catch (NullPointerException e){
            LogInfo.setErrorData(e.getCause() + "\n" + e.getMessage());
        }
    }

    private static HSSFWorkbook getTemplateBook(){
        String excelFilePath = new File("." + "/template/template_last_page.xls").getAbsolutePath();
        HSSFWorkbook srcWorkbook;
        try {
            FileInputStream fis = new FileInputStream(excelFilePath.trim());
            srcWorkbook = new HSSFWorkbook(fis);
            return srcWorkbook;
        }catch(Exception ex){
            LogInfo.setErrorData(ex.getMessage());
            return new HSSFWorkbook();
        }
    }


    private static void copyRow(HSSFWorkbook srcWorkbook, HSSFWorkbook dstWorkbook) {

        HSSFSheet dstWorksheet = dstWorkbook.getSheetAt(0);
        HSSFSheet srcWorksheet = srcWorkbook.getSheetAt(0);

        CellRangeAddress srcRangeAddress = CellRangeAddress.valueOf("A1:J20");


        int oldRowNum = dstWorksheet.getLastRowNum();

        for(int rowNum = srcRangeAddress.getFirstRow(); rowNum < srcRangeAddress.getLastRow(); rowNum++){

            HSSFRow newRow = dstWorksheet.getRow(dstWorksheet.getLastRowNum()+1);
            HSSFRow sourceRow = srcWorksheet.getRow(rowNum);

            // If the row exist in destination, push down all rows by 1 else create a new row
            if (newRow == null) {
                newRow = dstWorksheet.createRow(dstWorksheet.getLastRowNum()+1);
            }

            if (sourceRow == null) {
                continue;
            }

            // Loop through source columns to add to new row
            for (int i = 0; i < srcRangeAddress.getLastColumn(); i++) {
                // Grab a copy of the old/new cell

                HSSFCell oldCell = sourceRow.getCell(i);
                HSSFCell newCell = newRow.createCell(i);

                // If the old cell is null jump to next cell
                if (oldCell == null) {
                    newCell = null;
                    continue;
                }

                // Copy style from old cell and apply to new cell
                HSSFCellStyle newCellStyle = dstWorkbook.createCellStyle();
                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());

                newCell.setCellStyle(newCellStyle);

                // Set the cell data type
                newCell.setCellType(oldCell.getCellTypeEnum());

                // Set the cell data value
                switch (oldCell.getCellTypeEnum()) {
                    case BLANK:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case STRING:
                        newCell.setCellValue(oldCell.getRichStringCellValue());
                        break;
                }
            }

        }

        for (CellRangeAddress mergeAddress: srcWorksheet.getMergedRegions()){
            dstWorksheet.addMergedRegion(
                    new CellRangeAddress(
                            (oldRowNum+1) + mergeAddress.getFirstRow(),
                            (oldRowNum+1) + mergeAddress.getLastRow(),
                            mergeAddress.getFirstColumn(),
                            mergeAddress.getLastColumn()
                    )
            );
        }

    }



    private static ReportContext setReportStrategy(DriverType driverType){
        ReportContext reportContext = new ReportContext();
        switch (driverType){
            case IEC870:
                reportContext.setReportStrategy(new Iec870ReportStrategy());
                break;
            case IEC850:
                reportContext.setReportStrategy(new Iec850ReportStrategy());
                break;
            case SPRECON850:
                reportContext.setReportStrategy(new Iec850SpreconReportStrategy());
                break;
            case SPRECON870:
                reportContext.setReportStrategy(new Iec870SpreconReportStrategy());
                break;
            default:
                break;
        }
        return reportContext;
    }

    private static void writeDataToFile(Object document, String filePath) throws IOException {
        try {
            File file = new File(filePath);

            FileOutputStream fos = new FileOutputStream(file);
            if (document instanceof XWPFDocument) {
                XWPFDocument doc = (XWPFDocument) document;
                doc.write(fos);
            } else if (document instanceof HSSFWorkbook) {
                HSSFWorkbook doc = (HSSFWorkbook) document;
                doc.write(fos);
            } else {
                throw new IOException();
            }
            fos.close();
            LogInfo.setLogDataWithTitle("Сохранен файл:", filePath);
        }catch (FileNotFoundException e){
            LogInfo.setErrorData(e.getMessage());
        }
    }

}
