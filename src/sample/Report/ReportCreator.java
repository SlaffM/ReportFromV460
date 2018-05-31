package sample.Report;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import sample.v460.PointParam;
import sample.v460.ResourceBean;


public class ReportCreator {

    public static void CreateDocFile(ArrayList<PointParam> pointParams) throws IOException {

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

        FileOutputStream fos = new FileOutputStream(new File("test.docx"));
        document.write(fos);
        fos.close();
    }

}
