package reportV460.Report.Formats;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import reportV460.Helpers.LogInfo;
import reportV460.Report.ReportContext;
import reportV460.Report.Strategy.Iec850SprStrategy;
import reportV460.Report.Strategy.Iec850Strategy;
import reportV460.Report.Strategy.Iec870SprStrategy;
import reportV460.Report.Strategy.Iec870Strategy;
import reportV460.v460.DriverType;
import reportV460.v460.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WordDocument implements ExtensionFormat {
    public String type = "docx";
    private DocumentFile file;
    private XWPFDocument document;

    public WordDocument(ArrayList<Point> points, DocumentFile documentFile){
        this.file = documentFile;
        document = new XWPFDocument();

        for(Point point: points){
            ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createDocTable(document, point);
        }

    }

    public String getFileName() {
        return file.getName();
    }

    private ReportContext setReportStrategy(DriverType driverType){
        ReportContext reportContext = new ReportContext();
        switch (driverType){
            case IEC870:
                reportContext.setReportStrategy(new Iec870Strategy());
                break;
            case IEC850:
                reportContext.setReportStrategy(new Iec850Strategy());
                break;
            case SPRECON850:
                reportContext.setReportStrategy(new Iec850SprStrategy());
                break;
            case SPRECON870:
                reportContext.setReportStrategy(new Iec870SprStrategy());
                break;
            default:
                break;
        }
        return reportContext;
    }

    @Override
    public void writeDocument() {
        try {
            File file = new File(getFileName());
            FileOutputStream fos = new FileOutputStream(file);
            document.write(fos);
            fos.close();
            LogInfo.setLogDataWithTitle("Сохранен файл", file.getAbsolutePath());
        }catch (FileNotFoundException e){
            LogInfo.setErrorData(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
