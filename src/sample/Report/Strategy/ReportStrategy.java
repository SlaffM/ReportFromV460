package sample.Report.Strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.v460.PointParam;

public interface ReportStrategy {
        void createDocTable(XWPFDocument document, PointParam pointParam);
        void createXlsTable(HSSFWorkbook document, PointParam pointParam);
}
