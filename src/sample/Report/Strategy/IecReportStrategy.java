package sample.Report.Strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import sample.Helpers.StyleDocument;
import sample.Report.ReportPanelTitle.ReportPanelTitle;
import sample.v460.PointParam;
import sample.v460.ResourceBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class IecReportStrategy implements ReportStrategy{

    private int rowsFromPrevPointTable = 2;
    private int rowsOffset = 1;
    private int colsForTitlePanel = 2;



    private ArrayList<ResourceBean>resourcebeansOfTS = new ArrayList<>();
    private ArrayList<ResourceBean>resourcebeansOfTI = new ArrayList<>();

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

    private LinkedHashMap createHeadersTitle(ReportPanelTitle reportPanelTitle){
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
    String[] createHeadersVariablesTI(){
        throw new ArrayIndexOutOfBoundsException();
    }

    private void splitBeansToTSTI(ArrayList<ResourceBean> resourceBeans){
        for(ResourceBean resourceBean : resourceBeans) {
            if (resourceBean.isVariableTI()) {
                resourcebeansOfTI.add(resourceBean);
            } else {
                resourcebeansOfTS.add(resourceBean);
            }
        }
    }

    private void addHeadersToVariablesTable(XWPFTable table, String[] headers){
        XWPFTableRow tableRowOne = table.getRow(0);
        for(int count=0; count<headers.length; count++){
            tableRowOne.getCell(count).setColor("779BFF");
            tableRowOne.getCell(count).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

            tableRowOne.setRepeatHeader(true);

            XWPFParagraph para1 = tableRowOne.getCell(count).getParagraphs().get(0);
            XWPFRun rh1 = para1.createRun();
            rh1.setFontSize(16);
            rh1.setFontFamily("Courier");

            tableRowOne.getCell(count).setText(headers[count]);
        }
    }
    private void addVariablesToVariablesTable(XWPFTable table, String[] headers, ArrayList<ResourceBean> resourceBeans){
        int rowCounter = 1;

        for(ResourceBean resourceBean : resourceBeans){
            XWPFTableRow tableRowOneTI = table.getRow(rowCounter);
            for(int colNum = 0; colNum < headers.length; colNum++){
                XWPFTableCell cell = tableRowOneTI.getCell(colNum);
                String prop;
                if (resourceBean.isVariableTI()){
                    prop = getPropertiesResourceBeanTI(resourceBean).get(colNum);
                }else{
                    prop = getPropertiesResourceBean(resourceBean).get(colNum);
                }
                cell.setText(prop);
            }
            rowCounter++;
        }
    }
    private void createTableVariablesPanel(XWPFDocument document, ArrayList<ResourceBean> resourceBeans){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        splitBeansToTSTI(resourceBeans);

        if (!resourcebeansOfTS.isEmpty()) {
            XWPFTable tableTS = document.createTable(resourcebeansOfTS.size()+1,createHeadersVariables().length);
            addHeadersToVariablesTable(tableTS, createHeadersVariables());
            addVariablesToVariablesTable(tableTS, createHeadersVariables(), resourcebeansOfTS);
        }

        if (!resourcebeansOfTI.isEmpty()) {
            XWPFTable tableTI = document.createTable(resourcebeansOfTI.size()+1,createHeadersVariablesTI().length);
            addHeadersToVariablesTable(tableTI, createHeadersVariablesTI());
            addVariablesToVariablesTable(tableTI, createHeadersVariablesTI(), resourcebeansOfTI);
        }
        resourcebeansOfTS.clear();
        resourcebeansOfTI.clear();
    }
    private void createTableTitlePanel(XWPFDocument document, ReportPanelTitle reportPanelTitle){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        /*XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setFontFamily("Times New Roman");
        r1.setTextPosition(100);*/

        LinkedHashMap<String, String> titleTable = createHeadersTitle(reportPanelTitle);
        XWPFTable table = document.createTable(titleTable.size(), colsForTitlePanel);

        int rowNum = 0;
        for (Map.Entry<String, String> title : titleTable.entrySet()) {
            XWPFTableRow row = table.getRow(rowNum);
            row.getCell(0).setText(title.getKey());
            row.getCell(1).setText(title.getValue());
            rowNum++;
        }

        /*

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


    }

    private void addHeadersToVariablesTableXls(HSSFWorkbook document, String[] headers){
        Sheet sheet = document.getSheet("Report");
        Row tableRow = sheet.createRow(sheet.getLastRowNum() + rowsOffset);

        CellStyle headerStyle = StyleDocument.createHeadingStyle(document);

        for(int count = 0; count < headers.length; count++){
            Cell cell = tableRow.createCell(count);
            cell.setCellValue(headers[count]);
            cell.setCellStyle(headerStyle);
        }
    }
    private void addVariablesToVariablesTableXls(HSSFWorkbook document, String[] headers, ArrayList<ResourceBean> resourceBeans) {
        Sheet sheet = document.getSheet("Report");

        CellStyle baseStyle = StyleDocument.createBaseStyle(document);

        for(ResourceBean resourceBean : resourceBeans){
            Row tableRow = sheet.createRow(sheet.getLastRowNum() + rowsOffset);
            for(int colNum = 0; colNum < headers.length; colNum++){
                Cell cell = tableRow.createCell(colNum);
                String prop;
                if (resourceBean.isVariableTI()){
                    prop = getPropertiesResourceBeanTI(resourceBean).get(colNum);
                }else{
                    prop = getPropertiesResourceBean(resourceBean).get(colNum);
                }
                cell.setCellValue(prop);
                cell.setCellStyle(baseStyle);
            }
        }

    }
    private void createXlsTableVariablesPanel(HSSFWorkbook document, ArrayList<ResourceBean> resourceBeans){
        Sheet sheet = document.getSheet("Report");

        splitBeansToTSTI(resourceBeans);

        if (!resourcebeansOfTS.isEmpty()) {
            addHeadersToVariablesTableXls(document, createHeadersVariables());
            addVariablesToVariablesTableXls(document, createHeadersVariables(), resourcebeansOfTS);
        }

        if (!resourcebeansOfTI.isEmpty()) {
            sheet.createRow(sheet.getLastRowNum() + rowsOffset);
            addHeadersToVariablesTableXls(document, createHeadersVariablesTI());
            addVariablesToVariablesTableXls(document, createHeadersVariablesTI(), resourcebeansOfTI);
        }
        resourcebeansOfTS.clear();
        resourcebeansOfTI.clear();

        sheet.createRow(sheet.getLastRowNum() + rowsFromPrevPointTable);

        for(int i = 0; i < createHeadersVariables().length; i++) {
            //sheet.setRepeatingRows(region);
            sheet.autoSizeColumn(i);
        }

    }
    private void createXlsTableTitlePanel(HSSFWorkbook document, ReportPanelTitle reportPanelTitle) {

        LinkedHashMap titleTable = createHeadersTitle(reportPanelTitle);
        Sheet sheet = document.getSheet("Report");

        CellStyle headerStyle = StyleDocument.createHeadingStyle(document);

        titleTable.forEach((key, value) -> {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue((String)key);
            cell.setCellStyle(headerStyle);
            row.createCell(1).setCellValue((String)titleTable.get(key));
        });

        sheet.createRow(sheet.getLastRowNum() + 1);
    }

    ArrayList<String> getPropertiesResourceBean(ResourceBean resourceBean){
        ArrayList<String> props = new ArrayList<>();
        props.add(resourceBean.getPanelLocation());
        props.add(resourceBean.getSystem());
        props.add(resourceBean.getVoltageClass());
        props.add(resourceBean.getConnectionTitle());
        props.add(resourceBean.getDevice());
        props.add(resourceBean.getSignalName());
        props.add(resourceBean.getStatusText());
        props.add(resourceBean.getAlarmClass());
        props.add(resourceBean.getRecourcesLabel());
        props.add(resourceBean.getShortSymbAddress());
        return props;
    }
    ArrayList<String> getPropertiesResourceBeanTI(ResourceBean resourceBean){
        ArrayList<String> props = new ArrayList<>();
        props.add(resourceBean.getPanelLocation());
        props.add(resourceBean.getSystem());
        props.add(resourceBean.getVoltageClass());
        props.add(resourceBean.getConnectionTitle());
        props.add(resourceBean.getDevice());
        props.add(resourceBean.getSignalName());
        props.add(resourceBean.getUnit());
        props.add(resourceBean.getKtransform());
        props.add(resourceBean.getRecourcesLabel());
        props.add(resourceBean.getShortSymbAddress());
        return props;
    }

}
