package sample.Report.Strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import sample.Report.ReportPanelTitle.ReportPanelTitle;
import sample.v460.PointParam;

import java.util.LinkedHashMap;

public interface ReportStrategy {
        void createDocTable(XWPFDocument document, PointParam pointParam);
        void createXlsTable(HSSFWorkbook document, PointParam pointParam);
}
