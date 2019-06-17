package reportV460.Report.Formats;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import reportV460.Helpers.LogInfo;
import reportV460.Helpers.StyleDocument;
import reportV460.Report.ReportContext;
import reportV460.Report.ReportPanelTitle.ReportPanelTitle;
import reportV460.Report.Strategy.*;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelDocument implements ExtensionFormat {
    public String type = "xls";
    private DocumentFile file;
    //private HSSFWorkbook document;
    private static HSSFWorkbook document;
    private static Sheet sheet;

    private static int twoRowsFromPrevPointTable = 2;
    public static int oneRowOffset = 1;
    private static int colsForTitlePanel = 2;
/*
    private static ArrayList<ResourceBean>resourcebeansOfTS = new ArrayList<>();
    private static ArrayList<ResourceBean>resourcebeansOfTI = new ArrayList<>();*/


    public ExcelDocument(ArrayList<Point> points, DocumentFile documentFile) {
        this.file = documentFile;
        initPropertiesSheetAndHeaderFooter();

        for(Point point: points){
            //point.getDriverContext().createXlsTable(document, point);
            //point.getDriverContext().setReportStrategy(point.getDriverType());
            createTable(point);

            /*ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createXlsTable(document, point);*/
        }

        addSignaturesToLastPage();
    }

    private void initPropertiesSheetAndHeaderFooter(){
        document = new HSSFWorkbook();
        document.createSheet("Report");
        sheet = document.getSheetAt(0);
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

        //ClassLoader classLoader = getClass().getClassLoader();
        //File last_page = new File(classLoader.getResource("template/template_last_page.xls").getPath());

        File last_page = new File("." + "/template/template_last_page.xls");

        String excelFilePath = last_page.getAbsolutePath();
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
/*

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
*/

    public static void createTable(Point point){
        createXlsTableTitlePanel(point.getReportPanelTitle());
        createXlsTableVariablesPanel(point);
    }

    private static void createXlsTableTitlePanel(ReportPanelTitle reportPanelTitle) {

        LinkedHashMap titleTable = reportPanelTitle.createHeaders();

        CellStyle headerStyle = StyleDocument.createHeadingStyle(document);
        CellStyle baseStyle = StyleDocument.createBaseStyle(document);

        titleTable.forEach((key, value) -> {
            Row row = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

            Cell cell;
            int colNumber = 0;
            for(; colNumber < 3; colNumber++){
                cell = row.createCell(colNumber);
                cell.setCellValue((String)key);
                cell.setCellStyle(headerStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 2));
            //sheet.setRepeatingRows(CellRangeAddress.valueOf("0:2"));

            cell = row.createCell(colNumber);
            cell.setCellValue((String)titleTable.get(key));
            cell.setCellStyle(baseStyle);
        });

        sheet.createRow(sheet.getLastRowNum() + oneRowOffset);
    }
    private static void createXlsTableVariablesPanel(Point point){

        //splitBeansToTSTI(point.getResourceBeans());

        ReportContext reportContext = point.getDriverContext();
        ReportStrategy reportStrategy = reportContext.getReportStrategy();

        if (!point.getResourcebeansOfTS().isEmpty()) {
            /*addHeadersToVariablesTableXls(reportStrategy.createHeadersVariablesTS());
            addVariablesToVariablesTableTS(reportStrategy.createHeadersVariablesTS(), point);*/
            addHeadersToVariablesTableXls(reportStrategy.getTitleTableTS());
            addVariablesToVariablesTableTS(point);

        }

        if (!point.getResourcebeansOfTI().isEmpty()) {
            sheet.createRow(sheet.getLastRowNum() + oneRowOffset);
            addHeadersToVariablesTableXls(reportStrategy.getTitleTableTI());
            addVariablesToVariablesTableTI(point);
        }

        point.clearBeansInPoint();

        addRowAndResizeCollumns(point);
    }
    private static void addHeadersToVariablesTableXls(LinkedHashMap<String, String> headers){
        Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

        CellStyle headerStyle = StyleDocument.createHeadingStyle(document);

        int count = 0;
        for(Map.Entry<String,String> header: headers.entrySet()){
            Cell cell = tableRow.createCell(count);
            cell.setCellValue(header.getKey());
            cell.setCellStyle(headerStyle);
            count++;
        }

        /*
        for(int count = 0; count < headers.values().size(); count++){
            Cell cell = tableRow.createCell(count);
            cell.setCellValue(headers.get()[count]);
            cell.setCellStyle(headerStyle);
        }*/
    }
    private static void addVariablesToVariablesTableTS(Point point) {
        CellStyle baseStyle = StyleDocument.createBaseStyle(document);

        for(ResourceBean resourceBean : point.getResourcebeansOfTS()){
            Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

            int colNum = 0;

            ReportContext reportContext = point.getDriverContext();
            ReportStrategy reportStrategy = reportContext.getReportStrategy();
            reportStrategy.createDataTemplateTS(resourceBean);
            Map<String, String> titles = reportStrategy.getTitleTableTS();
            for (Map.Entry<String, String> title : titles.entrySet()) {

                Cell cell = tableRow.createCell(colNum);
                String prop = title.getValue();

                cell.setCellValue(prop);
                cell.setCellStyle(baseStyle);
                /*XWPFTableRow row = table.getRow(rowNum);
                row.getCell(0).setText(title.getKey());
                row.getCell(1).setText(title.getValue());
*/
                colNum++;
            }
            /*
            for(int colNum = 0; colNum < headers.length; colNum++){
                Cell cell = tableRow.createCell(colNum);
                String prop = point.getDriverContext().getReportStrategy().createDataTemplateTS(resourceBean).get();

                cell.setCellValue(prop);
                cell.setCellStyle(baseStyle);
            }*/
        }

    }

    private static void addVariablesToVariablesTableTI(Point point) {
        CellStyle baseStyle = StyleDocument.createBaseStyle(document);

        for(ResourceBean resourceBean : point.getResourcebeansOfTI()){
            Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

            int colNum = 0;

            ReportContext reportContext = point.getDriverContext();
            ReportStrategy reportStrategy = reportContext.getReportStrategy();
            reportStrategy.createDataTemplateTI(resourceBean);
            Map<String, String> titles = reportStrategy.getTitleTableTI();
            for (Map.Entry<String, String> title : titles.entrySet()) {

                Cell cell = tableRow.createCell(colNum);
                String prop = title.getValue();

                cell.setCellValue(prop);
                cell.setCellStyle(baseStyle);
                /*XWPFTableRow row = table.getRow(rowNum);
                row.getCell(0).setText(title.getKey());
                row.getCell(1).setText(title.getValue());
*/
                colNum++;
            }
            /*
            for(int colNum = 0; colNum < headers.length; colNum++){
                Cell cell = tableRow.createCell(colNum);
                String prop = point.getDriverContext().getReportStrategy().createDataTemplateTS(resourceBean).get();

                cell.setCellValue(prop);
                cell.setCellStyle(baseStyle);
            }*/
        }

    }

    private static void addRowAndResizeCollumns(Point point){
        sheet.createRow(sheet.getLastRowNum() + twoRowsFromPrevPointTable);

        ReportContext reportContext = point.getDriverContext();
        ReportStrategy reportStrategy = reportContext.getReportStrategy();


        for(int i = 0; i < reportStrategy.getTitleTableTI().size(); i++) {
            //sheet.setRepeatingRows(region);
            sheet.autoSizeColumn(i);
        }
    }

    /*private static void splitBeansToTSTI(List<ResourceBean> resourceBeans){
        for(ResourceBean resourceBean : resourceBeans) {
            if (resourceBean.isVariableTI()) {
                resourcebeansOfTI.add(resourceBean);
            } else {
                resourcebeansOfTS.add(resourceBean);
            }
        }
    }*/






}
