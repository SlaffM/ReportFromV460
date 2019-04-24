package sample.Report.Strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.*;
import sample.v460.ResourceBean;
import java.util.ArrayList;

public class Iec870ReportStrategy extends IecReportStrategy {

    @Override
    String[] createHeadersVariables(){
        String[] variablesTableHeaders = new String[]{
                "№ панели",
                "Система",
                "Класс напряж.",
                "Присоединение",
                "Устройство",
                "Наименование сигнала",
                "Текс состояния",
                "Класс тревог",
                "Тип АСДУ",
                "Адрес АСДУ",
                "Адрес IEC"
        };
        return variablesTableHeaders;
    }

    @Override
    ArrayList<String> getPropsBean(ResourceBean resourceBean){
        ArrayList props = new ArrayList();
        props.add(resourceBean.getPanelLocation());
        props.add(resourceBean.getSystem());
        props.add(resourceBean.getVoltageClass());
        props.add(resourceBean.getConnectionTitle());
        props.add(resourceBean.getDevice());
        props.add(resourceBean.getSignalName());
        props.add(resourceBean.getStatusText());
        props.add(resourceBean.getAlarmClass());
        props.add(resourceBean.getIec870_type());
        props.add(resourceBean.getIec870_coa1());
        props.add(resourceBean.getIec870_ioa1());
        return props;
    }

    @Override
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
            tableRowOne.getCell(8).setText(resourceBean.getIec870_type());
            tableRowOne.getCell(9).setText(resourceBean.getIec870_coa1());
            tableRowOne.getCell(10).setText(resourceBean.getIec870_ioa1());
            rowCounter++;
        }

        return table;
    }

    /*@Override
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
            tableRow.createCell(8).setCellValue(resourceBean.getIec870_type());
            tableRow.createCell(9).setCellValue(resourceBean.getIec870_coa1());
            tableRow.createCell(10).setCellValue(resourceBean.getIec870_ioa1());
        }

        int rowEnd = sheet.getLastRowNum();

        CellRangeAddress region = new CellRangeAddress(rowStart,rowEnd, 0,variablesTableHeaders.length-1);
        drawBorders(region, sheet);

        sheet.createRow(sheet.getLastRowNum() + 2);

        for(int i = 0; i < variablesTableHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }*/

}
