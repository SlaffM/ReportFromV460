package sample.Report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.Report.Strategy.ReportStrategy;
import sample.v460.PointParam;

public class ReportContext {

    ReportStrategy reportStrategy;

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

    public void createDocTable(XWPFDocument document, PointParam pointParam){
        reportStrategy.createDocTable(document, pointParam);
    }

    public void createXlsTable(HSSFWorkbook document, PointParam pointParam){
        reportStrategy.createXlsTable(document, pointParam);
    }


}
