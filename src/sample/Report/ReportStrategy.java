package sample.Report;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import sample.v460.PointParam;
import sample.v460.ResourceBean;

import java.util.ArrayList;

public interface ReportStrategy {

        void createTable(XWPFDocument document, PointParam pointParam);

}
