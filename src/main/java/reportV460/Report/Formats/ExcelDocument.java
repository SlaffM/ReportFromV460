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
    private HSSFWorkbook document;
    private Sheet sheet;

    private int twoRowsFromPrevPointTable = 2;
    private int oneRowOffset = 1;
    private int colsForTitlePanel = 2;

    ExcelDocument(ArrayList<Point> points, DocumentFile documentFile) {
        this.file = documentFile;
        initPropertiesDocument();
        points.forEach(this::createTables);
        addSignaturesToLastPage();
    }

    public void initPropertiesDocument(){
        document = new HSSFWorkbook();
        document.createSheet("Report");
        sheet = document.getSheetAt(0);

        sheet.setMargin(Sheet.LeftMargin, 0.252);
        sheet.setMargin(Sheet.RightMargin, 0.252);
        sheet.setMargin(Sheet.TopMargin, 0.752);
        sheet.setMargin(Sheet.BottomMargin, 0.752);

        sheet.setAutobreaks(true);
        sheet.setFitToPage(true);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        printSetup.setFitHeight((short)0);
        printSetup.setFitWidth((short)1);

        printSetup.setFooterMargin(0.33);
        printSetup.setHeaderMargin(0.33);

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

    public void createTables(Point point){
        createPanelTitle(point.getReportPanelTitle());
        createPanelVariables(point);
    }

    public void createPanelTitle(ReportPanelTitle reportPanelTitle) {

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
            sheet.addMergedRegion(new CellRangeAddress(
                    sheet.getLastRowNum(),
                    sheet.getLastRowNum(),
                    0,
                    2)
            );
            //sheet.setRepeatingRows(CellRangeAddress.valueOf("0:2"));

            cell = row.createCell(colNumber);
            cell.setCellValue((String)titleTable.get(key));
            cell.setCellStyle(baseStyle);
        });

        addRows(oneRowOffset);
    }
    public void createPanelVariables(Point point){

        ReportContext reportContext = point.getDriverContext();
        ReportStrategy reportStrategy = reportContext.getReportStrategy();

        if (!point.getResourcebeansOfTS().isEmpty()) {
            addVariablesToVariablesPanel(reportStrategy, point.getResourcebeansOfTS());
        }
        if (!point.getResourcebeansOfTI().isEmpty()) {
            addRows(oneRowOffset);
            addVariablesToVariablesPanel(reportStrategy, point.getResourcebeansOfTI());
        }
        point.clearBeansInPoint();
        addRows(twoRowsFromPrevPointTable);
    }

    public void addHeadersToVariablesPanel(Map<String, String> headers){
        Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

        CellStyle headerStyle = StyleDocument.createHeadingStyle(document);

        int count = 0;
        for(Map.Entry<String,String> header: headers.entrySet()){
            Cell cell = tableRow.createCell(count);
            cell.setCellValue(header.getKey());
            cell.setCellStyle(headerStyle);
            count++;
        }
    }
    public void addVariablesToVariablesPanel(ReportStrategy reportStrategy, ArrayList<ResourceBean> resourceBeans) {
        CellStyle baseStyle = StyleDocument.createBaseStyle(document);

        int cols = 0;
        int rowTable = 0;

        Map<String, String> titles;
        for(ResourceBean resourceBean : resourceBeans){
            titles = reportStrategy.createDataHeaders(resourceBean);

            if (rowTable == 0){
                addHeadersToVariablesPanel(titles);
                cols = titles.size();
            }
            Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

            int colNum = 0;
            for (Map.Entry<String, String> title : titles.entrySet()) {
                Cell cell = tableRow.createCell(colNum);
                cell.setCellValue(title.getValue());
                cell.setCellStyle(baseStyle);
                colNum++;
            }
            rowTable++;
        }

        resizeCollumns(cols);
    }

    private void addRows(int countRows){
        sheet.createRow(sheet.getLastRowNum() + countRows);
    }
    private void resizeCollumns(int countCollumns){
        for(int i = 0; i < countCollumns; i++){ sheet.autoSizeColumn(i); }
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

}
