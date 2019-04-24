package sample.Report.Strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import sample.Report.ReportPanelTitle.ReportPanelTitle;
import sample.v460.PointParam;
import sample.v460.ResourceBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IecReportStrategy implements ReportStrategy{

    @Override
    public void createDocTable(XWPFDocument document, PointParam pointParam) {

        CTBody body = document.getDocument().getBody();
        if(!body.isSetSectPr()){
            body.addNewSectPr();
        }

        CTSectPr section = body.getSectPr();
        if(!section.isSetPgSz()){
            section.addNewPgSz();
        }

        CTPageSz pageSize = section.getPgSz();
        pageSize.setOrient(STPageOrientation.LANDSCAPE);
        pageSize.setH(BigInteger.valueOf(16840));
        pageSize.setW(BigInteger.valueOf(23820));

        /*if(orientation.equals("landscape")){
            pageSize.setOrient(STPageOrientation.LANDSCAPE);
            //A4 = 595x842 -multiply 20 since BigInteger represents 1/20 Point
            //A3 = 842x1191
            pageSize.setH(BigInteger.valueOf(16840));
            pageSize.setW(BigInteger.valueOf(23820));
        }else{
            pageSize.setOrient(STPageOrientation.PORTRAIT);
            //A4 = 595x842 -multiply 20 since BigInteger represents 1/20 Point
            //A3 = 842x1191
            pageSize.setH(BigInteger.valueOf(23820));
            pageSize.setW(BigInteger.valueOf(16840));
        }*/

        createTableTitlePanel(document, pointParam.getReportPanelTitle());
        createTableVariablesPanel(document, pointParam.getResourceBeans());

        //XWPFParagraph para = document.createParagraph();
        //XWPFRun run = para.createRun();
        //run.addBreak();

        //XWPFParagraph para = document.createParagraph();
        //XWPFRun run = para.createRun();
        //run.setText(reportPanelTitle.getPanelLocation());
        //CTP ctp = para.getCTP();
        //CTPPr br = ctp.addNewPPr();
        //br.setSectPr(section);

    }
    @Override
    public void createXlsTable(HSSFWorkbook document, PointParam pointParam) {
        createXlsTableTitlePanel(document, pointParam.getReportPanelTitle());
        createXlsTableVariablesPanel(document, pointParam.getResourceBeans());
    }

    LinkedHashMap createHeadersTitle(ReportPanelTitle reportPanelTitle){
        LinkedHashMap titleTable = new LinkedHashMap<String,String>();

        titleTable.put("Расположение", reportPanelTitle.getPanelLocation());
        titleTable.put("Наименование шкафа", reportPanelTitle.getPanelTitle());
        titleTable.put("Наименование присоединения", reportPanelTitle.getConnectionTitle());
        titleTable.put("Обозначение контроллера", reportPanelTitle.getControllerTitle());
        titleTable.put("IP-адрес", reportPanelTitle.getIpAddress());

        return titleTable;
    }
    String[] createHeadersVariables(){
        throw new ArrayIndexOutOfBoundsException();
    }

    XWPFTable createTableTitlePanel(XWPFDocument document, ReportPanelTitle reportPanelTitle){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        XWPFTable table = document.createTable();

        /*XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setFontFamily("Times New Roman");
        r1.setTextPosition(100);*/

        LinkedHashMap titleTable = createHeadersTitle(reportPanelTitle);

        titleTable.forEach((key, value) -> {
            XWPFTableRow row = table.getRow(0);
            row.getCell(0).setText((String)key);
            row.getCell(1).setText((String)titleTable.get(key));
        });

        /*
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("Расположение");
        tableRowOne.addNewTableCell().setText(reportPanelTitle.getPanelLocation());

        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText("Наименование шкафа");
        tableRowTwo.getCell(1).setText(reportPanelTitle.getPanelTitle());

        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("Наименование присоединения");
        tableRowThree.getCell(1).setText(reportPanelTitle.getConnectionTitle());

        XWPFTableRow tableRowFour = table.createRow();
        tableRowFour.getCell(0).setText("Обозначение контроллера");
        tableRowFour.getCell(1).setText("Шкаф МТ10. Сервер SCADA");

        XWPFTableRow tableRowFive = table.createRow();
        tableRowFive.getCell(0).setText("IP-адрес");
        tableRowFive.getCell(1).setText(reportPanelTitle.getIpAddress());

        for (XWPFTableRow row : table.getRows()) {

            XWPFParagraph p1 = row.getCell(0).getParagraphs().get(0);
            p1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);

            *//*for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    for (XWPFRun run_p : paragraph.getRuns()) {
                        //run_p.setBold(true);
                    }
                }
            }*//*
        }
        */


        return table;
    }
    void createXlsTableTitlePanel(HSSFWorkbook document, ReportPanelTitle reportPanelTitle) {

        LinkedHashMap titleTable = createHeadersTitle(reportPanelTitle);

        Sheet sheet = document.getSheet("Report");

        int rowStart = sheet.getLastRowNum() + 1;

        titleTable.forEach((key, value) -> {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue((String)key);
            cell.setCellStyle(setHeaderCellStyle(document));
            row.createCell(1).setCellValue((String)titleTable.get(key));

        });

        int rowEnd = sheet.getLastRowNum();

        CellRangeAddress region = new CellRangeAddress(rowStart,rowEnd, 0,1);
        drawBorders(region, sheet);
        sheet.createRow(sheet.getLastRowNum() + 1);
    }

    XWPFTable createTableVariablesPanel(XWPFDocument document, ArrayList<ResourceBean> resourceBeans){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        String[] variablesTableHeaders = createHeadersVariables();

        XWPFTable table = document.createTable(resourceBeans.size()+1,variablesTableHeaders.length);

        XWPFTableRow tableRowOne = table.getRow(0);
        for(int count=0; count<variablesTableHeaders.length; count++){
            tableRowOne.getCell(count).setColor("779BFF");
            tableRowOne.getCell(count).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

            tableRowOne.setRepeatHeader(true);
            XWPFParagraph para1 = tableRowOne.getCell(count).getParagraphs().get(0);

            XWPFRun rh = para1.createRun();

            // style cell as desired

            rh.setFontSize(16);
            rh.setFontFamily("Courier");

            tableRowOne.getCell(count).setText(variablesTableHeaders[count]);
        }
        int rowCounter = 1;
        for(ResourceBean resourceBean : resourceBeans){
            tableRowOne = table.getRow(rowCounter);
            tableRowOne.getCell(0).setText(resourceBean.getPanelLocation());
            tableRowOne.getCell(1).setText(resourceBean.getSystem());
            tableRowOne.getCell(2).setText(resourceBean.getVoltageClass());
            tableRowOne.getCell(3).setText(resourceBean.getConnectionTitle());
            tableRowOne.getCell(4).setText(resourceBean.getDevice());
            tableRowOne.getCell(5).setText(resourceBean.getSignalName());
            tableRowOne.getCell(6).setText(resourceBean.getStatusText());
            tableRowOne.getCell(7).setText(resourceBean.getAlarmClass());
            tableRowOne.getCell(8).setText(resourceBean.getRecourcesLabel());
            tableRowOne.getCell(9).setText(resourceBean.getShortSymbAddress());
            rowCounter++;
        }


        return table;
    }
    void createXlsTableVariablesPanel(HSSFWorkbook document, ArrayList<ResourceBean> resourceBeans){

        String[] variablesTableHeaders = createHeadersVariables();

        Sheet sheet = document.getSheet("Report");

        Row tableRow = sheet.createRow(sheet.getLastRowNum() + 1);
        for(int count = 0; count < variablesTableHeaders.length; count++){
            Cell cell = tableRow.createCell(count);
            cell.setCellValue(variablesTableHeaders[count]);
            cell.setCellStyle(setHeaderCellStyle(document));
        }

        int rowStart = sheet.getLastRowNum();

        for(ResourceBean resourceBean : resourceBeans){
            tableRow = sheet.createRow(sheet.getLastRowNum() + 1);

            tableRow.createCell(0).setCellValue(resourceBean.getPanelLocation());
            tableRow.createCell(1).setCellValue(resourceBean.getSystem());
            tableRow.createCell(2).setCellValue(resourceBean.getVoltageClass());
            tableRow.createCell(3).setCellValue(resourceBean.getConnectionTitle());
            tableRow.createCell(4).setCellValue(resourceBean.getDevice());
            tableRow.createCell(5).setCellValue(resourceBean.getSignalName());
            tableRow.createCell(6).setCellValue(resourceBean.getStatusText());
            tableRow.createCell(7).setCellValue(resourceBean.getAlarmClass());
            tableRow.createCell(8).setCellValue(resourceBean.getRecourcesLabel());
            tableRow.createCell(9).setCellValue(resourceBean.getShortSymbAddress());
        }

        int rowEnd = sheet.getLastRowNum();

        CellRangeAddress region = new CellRangeAddress(rowStart,rowEnd, 0,variablesTableHeaders.length-1);
        drawBorders(region, sheet);

        sheet.createRow(sheet.getLastRowNum() + 2);

        for(int i = 0; i < variablesTableHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    void drawBorders(CellRangeAddress region, Sheet sheet){
        BorderStyle borderStyle = BorderStyle.THIN;
        BorderExtent borderExtent = BorderExtent.ALL;
        PropertyTemplate propertyTemplate = new PropertyTemplate();
        propertyTemplate.drawBorders(region, borderStyle, borderExtent);
        propertyTemplate.applyBorders(sheet);
    }
    CellStyle setHeaderCellStyle(HSSFWorkbook document){
        Font headerFont = document.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = document.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);

        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFont);
        return headerCellStyle;
    }


}
