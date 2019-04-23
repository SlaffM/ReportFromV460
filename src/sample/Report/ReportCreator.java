package sample.Report;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xwpf.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
import sample.Report.Strategy.*;
import sample.v460.PointParam;


public class ReportCreator {

    public static void CreateDocFile(ArrayList<PointParam> pointParams, String docFile) throws IOException {

        XWPFDocument document = new XWPFDocument();
        ReportContext reportContext = new ReportContext();

        for(PointParam point: pointParams){
            switch (point.getDriverType()){
                case IEC870: case SPRECON870:
                    reportContext.setReportStrategy(new Iec870ReportStrategy());
                    break;
                case IEC850:
                    reportContext.setReportStrategy(new Iec850ReportStrategy());
                    break;
                case SPRECON850:
                    reportContext.setReportStrategy(new Iec850SpreconReportStrategy());
                    break;
                default:
                    break;
            }
            reportContext.createTable(document, point);
        }

        FileOutputStream fos = new FileOutputStream(new File(docFile));
        document.write(fos);
        fos.close();
    }

}
