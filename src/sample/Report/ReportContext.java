package sample.Report;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.Report.Strategy.ReportStrategy;
import sample.v460.PointParam;

public class ReportContext {

    ReportStrategy reportStrategy;

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

    public void createTable(XWPFDocument document, PointParam pointParam){
        reportStrategy.createTable(document, pointParam);
    }


}
