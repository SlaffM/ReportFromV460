package sample.Report;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import sample.v460.Iec870VariableType;
import sample.v460.PointParam;
import sample.v460.ResourceBean;

import java.math.BigInteger;
import java.util.ArrayList;

public class Iec870ReportStrategy implements ReportStrategy{

    public void createTable(XWPFDocument document, PointParam pointParam) {
        createTableForPoint(document, pointParam);
    }

    private void createTableForPoint(XWPFDocument document, PointParam pointParam){


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

        XWPFTable panelTitleTable = createTableTitlePanel(document, pointParam.getReportPanelTitle());
        XWPFTable panelVariablesTable = createTableVariablesPanel(document, pointParam.getResourceBeans());

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


    private XWPFTable createTableVariablesPanel(XWPFDocument document, ArrayList<ResourceBean> resourceBeans){
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
            tableRowOne.getCell(6).setText(resourceBean.getMatrix());
            tableRowOne.getCell(7).setText(resourceBean.getAlarmClass());
            tableRowOne.getCell(8).setText(resourceBean.getIec870_type());
            tableRowOne.getCell(9).setText(resourceBean.getIec870_coa1());
            tableRowOne.getCell(10).setText(resourceBean.getIec870_ioa1());
            rowCounter++;
        }

        return table;
    }


    private XWPFTable createTableTitlePanel(XWPFDocument document, ReportPanelTitle reportPanelTitle){
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




}
