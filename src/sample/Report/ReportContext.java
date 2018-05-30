package sample.Report;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import sample.v460.PointParam;
import sample.v460.ResourceBean;

import java.util.ArrayList;

public class ReportContext {

    ReportStrategy reportStrategy;

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

    public void createTable(XWPFDocument document, PointParam pointParam){
        reportStrategy.createTable(document, pointParam);
    }


}
