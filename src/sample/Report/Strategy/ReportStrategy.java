package sample.Report.Strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.v460.Point;

public interface ReportStrategy {
        void createDocTable(XWPFDocument document, Point point);
        void createXlsTable(HSSFWorkbook document, Point point);
}
