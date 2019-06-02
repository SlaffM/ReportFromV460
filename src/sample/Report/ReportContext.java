package sample.Report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.Report.Strategy.ReportStrategy;
import sample.v460.Point;

public class ReportContext {

    ReportStrategy reportStrategy;

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

    public void createDocTable(XWPFDocument document, Point point){
        reportStrategy.createDocTable(document, point);
    }

    public void createXlsTable(HSSFWorkbook document, Point point){
        reportStrategy.createXlsTable(document, point);
    }



}
