package reportV460.Report.Strategy;

import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public class IecStrategy implements ReportStrategy{

/*
    public LinkedHashMap getTitleTableTS() {
        return titleTableTS;
    }

    public LinkedHashMap getTitleTableTI() {
        return titleTableTI;
    }*/

    /*    private int twoRowsFromPrevPointTable = 2;
    public int oneRowOffset = 1;
    private int colsForTitlePanel = 2;*/


    /*private ArrayList<ResourceBean>resourcebeansOfTS = new ArrayList<>();
    private ArrayList<ResourceBean>resourcebeansOfTI = new ArrayList<>();*/


    @Override
    /*public void createDocTable(XWPFDocument document, Point point) {

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

        *//*if(orientation.equals("landscape")){
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
        }*//*

        createTableTitlePanel(document, point.getReportPanelTitle());
        createTableVariablesPanel(document, point.getResourceBeans());

        //XWPFParagraph para = document.createParagraph();
        //XWPFRun run = para.createRun();
        //run.addBreak();

        //XWPFParagraph para = document.createParagraph();
        //XWPFRun run = para.createRun();
        //run.setText(reportPanelTitle.getPanelLocation());
        //CTP ctp = para.getCTP();
        //CTPPr br = ctp.addNewPPr();
        //br.setSectPr(section);

    }*/
    /*@Override
    public void createXlsTable(HSSFWorkbook document, Point point) {

        ExcelDocument.createTables(point);

        *//*createXlsTableTitlePanel(document, point.getReportPanelTitle());
        createXlsTableVariablesPanel(document, point.getResourceBeans());*//*
    }*/

    /*private LinkedHashMap createHeadersTitle(ReportPanelTitle reportPanelTitle){
        LinkedHashMap titleTable = new LinkedHashMap<String,String>();

        titleTable.put("Расположение", reportPanelTitle.getPanelLocation());
        titleTable.put("Наименование шкафа", reportPanelTitle.getPanelTitle());
        titleTable.put("Наименование присоединения", reportPanelTitle.getConnectionTitle());
        titleTable.put("Обозначение контроллера", reportPanelTitle.getControllerTitle());
        titleTable.put("IP-адрес", reportPanelTitle.getIpAddress());

        return titleTable;
    }*/
/*

    public String[] createHeadersVariablesTS(){
        throw new ArrayIndexOutOfBoundsException();
    }
    public String[] createHeadersVariablesTI(){
        throw new ArrayIndexOutOfBoundsException();
    }
*/



    /*private void splitBeansToTSTI(List<ResourceBean> resourceBeans){
        for(ResourceBean resourceBean : resourceBeans) {
            if (resourceBean.isVariableTI()) {
                resourcebeansOfTI.add(resourceBean);
            } else {
                resourcebeansOfTS.add(resourceBean);
            }
        }
    }*/







/*

    private void createTableTitlePanel(XWPFDocument document, ReportPanelTitle reportPanelTitle){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        */
/*XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setFontFamily("Times New Roman");
        r1.setTextPosition(100);*//*
*/
/*

        LinkedHashMap<String, String> titleTable = createHeadersTitle(reportPanelTitle);
        XWPFTable table = document.createTables(titleTable.size(), colsForTitlePanel);

        int rowNum = 0;
        for (Map.Entry<String, String> title : titleTable.entrySet()) {

            XWPFTableRow row = table.getRow(rowNum);
            row.getCell(0).setText(title.getKey());
            row.getCell(1).setText(title.getValue());

            *//*
*/
/*XWPFParagraph p1 = row.getCell(0).getParagraphs().get(0);
            p1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);*//*
*/
/*

            rowNum++;
        }*//*


        */
/*

        for (XWPFTableRow row : table.getRows()) {

            XWPFParagraph p1 = row.getCell(0).getParagraphs().get(0);
            p1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);

            *//*
*/
/*for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    for (XWPFRun run_p : paragraph.getRuns()) {
                        //run_p.setBold(true);
                    }
                }
            }*//*
*/
/*
        }
        *//*



    }
    private void createTableVariablesPanel(XWPFDocument document, List<ResourceBean> resourceBeans){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        splitBeansToTSTI(resourceBeans);

        if (!resourcebeansOfTS.isEmpty()) {
            XWPFTable tableTS = document.createTables(resourcebeansOfTS.size()+1, createHeadersVariablesTS().length);
            addHeadersToVariablesTable(tableTS, createHeadersVariablesTS());
            addVariablesToVariablesTable(tableTS, createHeadersVariablesTS(), resourcebeansOfTS);
        }

        if (!resourcebeansOfTI.isEmpty()) {
            XWPFTable tableTI = document.createTables(resourcebeansOfTI.size()+1,createHeadersVariablesTI().length);
            addHeadersToVariablesTable(tableTI, createHeadersVariablesTI());
            addVariablesToVariablesTable(tableTI, createHeadersVariablesTI(), resourcebeansOfTI);
        }
        resourcebeansOfTS.clear();
        resourcebeansOfTI.clear();
    }
    private void addHeadersToVariablesTable(XWPFTable table, String[] headers){
        XWPFTableRow tableRowOne = table.getRow(0);
        for(int count=0; count<headers.length; count++){

            XWPFParagraph para = tableRowOne.getCell(count).getParagraphs().get(0);
            para.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun rh = para.createRun();
            rh.setFontSize(16);
            rh.setFontFamily("Courier");
            rh.setColor("779BFF");

            tableRowOne.getCell(count).setText(headers[count]);

*/
/*


            tableRowOne.getCell(count).setColor("779BFF");
            tableRowOne.getCell(count).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

            tableRowOne.setRepeatHeader(true);

            XWPFParagraph para1 = tableRowOne.getCell(count).getParagraphs().get(0);
            XWPFRun rh1 = para1.createRun();
            rh1.setFontSize(16);
            rh1.setFontFamily("Courier");

            tableRowOne.getCell(count).setText(headers[count]);*//*

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
                    prop = getPropertiesResourceBeanTS(resourceBean).get(colNum);
                }
                cell.setText(prop);
            }
            rowCounter++;
        }
    }
*/

    /*private void createXlsTableTitlePanel(HSSFWorkbook document, ReportPanelTitle reportPanelTitle) {

        LinkedHashMap titleTable = createHeadersTitle(reportPanelTitle);
        Sheet sheet = document.getSheetAt(0);

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
    private void createXlsTableVariablesPanel(HSSFWorkbook document, List<ResourceBean> resourceBeans){
        Sheet sheet = document.getSheetAt(0);

        splitBeansToTSTI(resourceBeans);

        if (!resourcebeansOfTS.isEmpty()) {
            addHeadersToVariablesTableXls(document, createHeadersVariablesTS());
            addVariablesToVariablesTableXls(document, createHeadersVariablesTS(), resourcebeansOfTS);
        }

        if (!resourcebeansOfTI.isEmpty()) {
            sheet.createRow(sheet.getLastRowNum() + oneRowOffset);
            addHeadersToVariablesTableXls(document, createHeadersVariablesTI());
            addVariablesToVariablesTableXls(document, createHeadersVariablesTI(), resourcebeansOfTI);
        }
        resourcebeansOfTS.clear();
        resourcebeansOfTI.clear();

        addRowAndResizeCollumns(sheet);
    }
    private void addHeadersToVariablesTableXls(HSSFWorkbook document, String[] headers){
        Sheet sheet = document.getSheetAt(0);
        Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);

        CellStyle headerStyle = StyleDocument.createHeadingStyle(document);

        for(int count = 0; count < headers.length; count++){
            Cell cell = tableRow.createCell(count);
            cell.setCellValue(headers[count]);
            cell.setCellStyle(headerStyle);
        }
    }
    private void addVariablesToVariablesTableXls(HSSFWorkbook document, String[] headers, ArrayList<ResourceBean> resourceBeans) {
        Sheet sheet = document.getSheetAt(0);

        CellStyle baseStyle = StyleDocument.createBaseStyle(document);

        for(ResourceBean resourceBean : resourceBeans){
            Row tableRow = sheet.createRow(sheet.getLastRowNum() + oneRowOffset);
            for(int colNum = 0; colNum < headers.length; colNum++){
                Cell cell = tableRow.createCell(colNum);
                String prop;
                if (resourceBean.isVariableTI()){
                    prop = getPropertiesResourceBeanTI(resourceBean).get(colNum);
                }else{
                    prop = getPropertiesResourceBeanTS(resourceBean).get(colNum);
                }
                cell.setCellValue(prop);
                cell.setCellStyle(baseStyle);
            }
        }

    }
    private void addRowAndResizeCollumns(Sheet sheet){
        sheet.createRow(sheet.getLastRowNum() + twoRowsFromPrevPointTable);

        for(int i = 0; i < createHeadersVariablesTS().length; i++) {
            //sheet.setRepeatingRows(region);
            sheet.autoSizeColumn(i);
        }
    }*/

    public LinkedHashMap<String, String> createDataTemplateTI(ResourceBean resourceBean){

        LinkedHashMap<String, String> titleTableTI = new LinkedHashMap();
        titleTableTI.put("№ панели", resourceBean.getPanelLocation());
        titleTableTI.put("Система", resourceBean.getSystem());
        titleTableTI.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTI.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTI.put("Устройство", resourceBean.getDevice());
        titleTableTI.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTI.put("Ед. измерения", resourceBean.getUnit());
        titleTableTI.put("Ктт, Ктн", resourceBean.getCoefficientTransform());
        titleTableTI.put("Тип АСДУ", resourceBean.getIec870_type());
        titleTableTI.put("Адрес АСДУ", resourceBean.getIec870_coa1());
        titleTableTI.put("Адрес объекта", resourceBean.getIec870_ioa1());

        return titleTableTI;
    }


    public LinkedHashMap<String,String> createDataTemplateTS(ResourceBean resourceBean){

        LinkedHashMap<String, String> titleTableTS = new LinkedHashMap();
        titleTableTS.put("№ панели", resourceBean.getPanelLocation());
        titleTableTS.put("Система", resourceBean.getSystem());
        titleTableTS.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTS.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTS.put("Устройство", resourceBean.getDevice());
        titleTableTS.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTS.put("Текс состояния", resourceBean.getStatusText());
        titleTableTS.put("Класс тревог", resourceBean.getAlarmClass());
        titleTableTS.put("Тип АСДУ", resourceBean.getIec870_type());
        titleTableTS.put("Адрес АСДУ", resourceBean.getIec870_coa1());
        titleTableTS.put("Адрес объект", resourceBean.getIec870_ioa1());

        return titleTableTS;

    }


/*

    public ArrayList<String> getPropertiesResourceBeanTS(ResourceBean resourceBean){
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
    public ArrayList<String> getPropertiesResourceBeanTI(ResourceBean resourceBean){
        ArrayList<String> props = new ArrayList<>();
        props.add(resourceBean.getPanelLocation());
        props.add(resourceBean.getSystem());
        props.add(resourceBean.getVoltageClass());
        props.add(resourceBean.getConnectionTitle());
        props.add(resourceBean.getDevice());
        props.add(resourceBean.getSignalName());
        props.add(resourceBean.getUnit());
        props.add(resourceBean.getCoefficientTransform());
        props.add(resourceBean.getRecourcesLabel());
        props.add(resourceBean.getShortSymbAddress());
        return props;
    }
*/

}
