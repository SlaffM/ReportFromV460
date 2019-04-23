package sample.Report.Strategy;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.v460.PointParam;

public interface ReportStrategy {

        void createTable(XWPFDocument document, PointParam pointParam);

}
