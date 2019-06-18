package reportV460.Report.Formats;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import reportV460.Helpers.LogInfo;
import reportV460.Report.ReportContext;
import reportV460.Report.ReportPanelTitle.ReportPanelTitle;
import reportV460.Report.Strategy.ReportStrategy;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordDocument implements ExtensionFormat {
    public String type = "docx";
    private DocumentFile file;
    private XWPFDocument document;
    private XWPFTable table;
    private int colsForTitlePanel = 2;

    public WordDocument(ArrayList<Point> points, DocumentFile documentFile){
        this.file = documentFile;

        initPropertiesDocument();

        for(Point point: points){
            createTables(point);
        }
    }

    public void initPropertiesDocument() {
        document = new XWPFDocument();

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
    }

    public String getFileName() {
        return file.getName();
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

    public void createTables(Point point) {
        createTitlePanel(point.getReportPanelTitle());
        createVariablesPanel(point);
    }

    public void createTitlePanel(ReportPanelTitle reportPanelTitle){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        LinkedHashMap<String, String> titleTable = reportPanelTitle.createHeaders();
        table = document.createTable(titleTable.size(), colsForTitlePanel);

        int rowNum = 0;
        for (Map.Entry<String, String> title : titleTable.entrySet()) {
            XWPFTableRow row = table.getRow(rowNum);
            row.getCell(0).setText(title.getKey());
            row.getCell(1).setText(title.getValue());
            rowNum++;
        }
    }
    public void createVariablesPanel(Point point){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

        ReportContext reportContext = point.getDriverContext();
        ReportStrategy reportStrategy = reportContext.getReportStrategy();

        if (!point.getResourcebeansOfTS().isEmpty()) {
            //table = document.createTable();//(point.getResourcebeansOfTS().size()+1,createHeadersVariables().length);
            //addHeadersToVariablesPanel(createHeadersVariables());
            //addVariablesToVariablesPanel(tableTS, createHeadersVariables(), resourcebeansOfTS);
            addVariablesToVariablesPanel(reportStrategy, point.getResourcebeansOfTS());

        }

        if (!point.getResourcebeansOfTI().isEmpty()) {
            //table = document.createTable();//resourcebeansOfTI.size()+1,createHeadersVariablesTI().length);
            //addHeadersToVariablesPanel(tableTI, createHeadersVariablesTI());
            //addVariablesToVariablesPanel(tableTI, createHeadersVariablesTI(), resourcebeansOfTI);
            addVariablesToVariablesPanel(reportStrategy, point.getResourcebeansOfTS());
        }
        point.clearBeansInPoint();
    }

    public void addHeadersToVariablesPanel(Map<String, String> headers){

        table = document.createTable(headers.size(), colsForTitlePanel);
        XWPFTableRow tableRowOne = table.getRow(0);

        int count = 0;
        for(Map.Entry<String,String> header: headers.entrySet()){
            /*Cell cell = tableRow.createCell(count);
            cell.setCellValue(header.getKey());
            cell.setCellStyle(headerStyle);
            count++;*/

            XWPFParagraph para = tableRowOne.getCell(count).getParagraphs().get(0);
            para.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun rh = para.createRun();
            rh.setFontSize(16);
            rh.setFontFamily("Courier");
            rh.setColor("779BFF");

            tableRowOne.getCell(count).setText(header.getKey());
        }

        /*
        for(int count=0; count<headers.length; count++){

            XWPFParagraph para = tableRowOne.getCell(count).getParagraphs().get(0);
            para.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun rh = para.createRun();
            rh.setFontSize(16);
            rh.setFontFamily("Courier");
            rh.setColor("779BFF");

            tableRowOne.getCell(count).setText(headers[count]);
        }*/
    }
    public void addVariablesToVariablesPanel(ReportStrategy reportStrategy, ArrayList<ResourceBean> resourceBeans){
        int rowTable = 1;

        Map<String, String> titles;
        for(ResourceBean resourceBean : resourceBeans){
            titles = reportStrategy.createData(resourceBean);

            if (rowTable == 1){
                addHeadersToVariablesPanel(titles);
                table = document.createTable(resourceBeans.size(), titles.size());
            }

            XWPFTableRow tableRow = table.getRow(rowTable);
            int colNum = 0;
            for (Map.Entry<String, String> title : titles.entrySet()) {
                XWPFTableCell cell = tableRow.getCell(colNum);
                cell.setText(title.getValue());

                /*Cell cell = tableRow.createCell(colNum);
                cell.setCellValue(title.getValue());
                cell.setCellStyle(baseStyle);
                colNum++;*/
            }
            rowTable++;

            /*
            for(int colNum = 0; colNum < headers.length; colNum++){
                XWPFTableCell cell = tableRowOneTI.getCell(colNum);
                String prop;
                if (resourceBean.isVariableTI()){
                    prop = getPropertiesResourceBeanTI(resourceBean).get(colNum);
                }else{
                    prop = getPropertiesResourceBean(resourceBean).get(colNum);
                }
                cell.setText(prop);
            }*/
        }
    }




}
