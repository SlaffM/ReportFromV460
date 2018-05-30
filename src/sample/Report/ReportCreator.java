package sample.Report;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import sample.v460.ResourceBean;


public class ReportCreator {

    public static void CreateDocFile(Map<String, ArrayList<ResourceBean>> points) throws IOException {

/*
        String[] sheetArray = new String[]{"one", "two", "three", "four"};
        XWPFDocument document = new XWPFDocument();

        for(String sheet: sheetArray){
            changeOrientation(document,sheet, "landscape");
        }*/

        XWPFDocument document = new XWPFDocument();

        for(Map.Entry<String, ArrayList<ResourceBean>> iecVariables: points.entrySet()){
            ReportPanelTitle reportPanelTitle = new ReportPanelTitle();
            reportPanelTitle.setTagname(iecVariables.getKey());

            changeOrientation(document, reportPanelTitle, iecVariables.getValue());
        }


        FileOutputStream fos = new FileOutputStream(new File("test.docx"));
        document.write(fos);
        fos.close();
    }

    private static void changeOrientation(XWPFDocument document, ReportPanelTitle reportPanelTitle, ArrayList<ResourceBean> iec870VariableTypes){


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

        XWPFTable panelTitleTable = createTableTitlePanel(document, reportPanelTitle);



        XWPFTable panelVariablesTable = createTableVariablesPanel(document, iec870VariableTypes);


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

    private static XWPFTable createTableTitlePanel(XWPFDocument document, ReportPanelTitle reportPanelTitle){
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
        tableRowFour.getCell(1).setText(reportPanelTitle.getControllerTitle());

        /*XWPFTableRow tableRowFive = table.createRow();
        tableRowFive.getCell(0).setText("IP-адрес");
        tableRowFive.getCell(1).setText(reportPanelTitle.getNetAddr());*/

        for (XWPFTableRow row : table.getRows()) {

            XWPFParagraph p1 = row.getCell(0).getParagraphs().get(0);
            p1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);



            /*for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    for (XWPFRun run_p : paragraph.getRuns()) {
                        //run_p.setBold(true);
                    }
                }
            }*/
        }


        return table;
    }



    private static XWPFTable createTableVariablesPanel(XWPFDocument document, ArrayList<ResourceBean> iec870VariableTypes){
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak();

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

        XWPFTable table = document.createTable(iec870VariableTypes.size()+1,variablesTableHeaders.length);

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
        /*for(Iec870VariableType iec870VariableType : iec870VariableTypes){
            tableRowOne = table.getRow(rowCounter);
            tableRowOne.getCell(0).setText(iec870VariableType.getPanelLocation());
            tableRowOne.getCell(1).setText(iec870VariableType.getSystem());
            tableRowOne.getCell(2).setText(iec870VariableType.getVoltageClass());
            tableRowOne.getCell(3).setText(iec870VariableType.getConnectionTitle());
            tableRowOne.getCell(4).setText(iec870VariableType.getDevice());
            tableRowOne.getCell(5).setText(iec870VariableType.getSignalName());
            tableRowOne.getCell(6).setText(iec870VariableType.getMatrix());
            tableRowOne.getCell(7).setText(iec870VariableType.getAlarmClass());
            tableRowOne.getCell(8).setText(iec870VariableType.getIec870_type());
            tableRowOne.getCell(9).setText(iec870VariableType.getIec870_coa1());
            tableRowOne.getCell(10).setText(iec870VariableType.getIec870_ioa1());
            rowCounter++;
        }*/

        return table;
    }


}
