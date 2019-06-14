package reportV460.Report.Formats;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import reportV460.Helpers.LogInfo;
import reportV460.Helpers.StyleDocument;
import reportV460.Report.ReportContext;
import reportV460.Report.Strategy.Iec850SprStrategy;
import reportV460.Report.Strategy.Iec850Strategy;
import reportV460.Report.Strategy.Iec870SprStrategy;
import reportV460.Report.Strategy.Iec870Strategy;
import reportV460.v460.DriverType;
import reportV460.v460.Point;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExcelDocument implements ExtensionFormat {
    public String type = "xls";
    private DocumentFile file;
    private HSSFWorkbook document;


    public ExcelDocument(ArrayList<Point> points, DocumentFile documentFile) {
        this.file = documentFile;
        this.document = new HSSFWorkbook();

        initPropertiesSheetAndHeaderFooter();

        for(Point point: points){
            ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createXlsTable(document, point);
        }

        addSignaturesToLastPage();
    }

    private void initPropertiesSheetAndHeaderFooter(){
        document.createSheet("Report");
        Sheet sheet = document.getSheetAt(0);
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

    private void addSignaturesToLastPage() {
        HSSFWorkbook templateWorkBook = getTemplateBook();
        try {
            copyRow(templateWorkBook, document);
        }catch (NullPointerException e){
            LogInfo.setErrorData(e.getCause() + "\n" + e.getMessage());
        }
    }

    private HSSFWorkbook getTemplateBook(){
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

    private void copyRow(HSSFWorkbook srcWorkbook, HSSFWorkbook dstWorkbook) {

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

    private String getFileName(){
        return this.file.getName();
    }

    @Override
    public void writeDocument() {
        try {
            File file = new File(getFileName());
            FileOutputStream fos = new FileOutputStream(file);
            document.write(fos);
            fos.close();
            LogInfo.setLogDataWithTitle("Сохранен файл", file.getAbsolutePath());
        }catch (FileNotFoundException e){
            LogInfo.setErrorData(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ReportContext setReportStrategy(DriverType driverType){
        ReportContext reportContext = new ReportContext();
        switch (driverType){
            case IEC870:
                reportContext.setReportStrategy(new Iec870Strategy());
                break;
            case IEC850:
                reportContext.setReportStrategy(new Iec850Strategy());
                break;
            case SPRECON850:
                reportContext.setReportStrategy(new Iec850SprStrategy());
                break;
            case SPRECON870:
                reportContext.setReportStrategy(new Iec870SprStrategy());
                break;
            default:
                break;
        }
        return reportContext;
    }
}
