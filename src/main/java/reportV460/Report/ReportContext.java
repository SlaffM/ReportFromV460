package reportV460.Report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import reportV460.Report.Strategy.ReportStrategy;
import reportV460.v460.Point;

public class ReportContext {

    ReportStrategy reportStrategy;

    public ReportStrategy getReportStrategy() {
        return reportStrategy;
    }

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

    /*public void createDocTable(XWPFDocument document, Point point){
        reportStrategy.createDocTable(document, point);
    }

    public void createXlsTable(HSSFWorkbook document, Point point){
        reportStrategy.createXlsTable(document, point);
    }*/



}
