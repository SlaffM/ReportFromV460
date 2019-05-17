package sample.Report;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xwpf.usermodel.*;
import sample.Report.Strategy.*;
import sample.v460.DriverType;
import sample.v460.PointParam;


public class ReportCreator {

    public static void CreateDocFile(ArrayList<PointParam> pointParams, String docFile) throws IOException {
        XWPFDocument document = new XWPFDocument();

        for(PointParam point: pointParams){
            ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createDocTable(document, point);
        }
        writeDataToFile(document, docFile);
    }

    public static void CreateXlsFile(ArrayList<PointParam> pointParams, String xlsFile) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook();
        book.createSheet("Report");

        for(PointParam point: pointParams){
            ReportContext reportContext = setReportStrategy(point.getDriverType());
            reportContext.createXlsTable(book, point);
        }
        writeDataToFile(book, xlsFile);
    }

    private static ReportContext setReportStrategy(DriverType driverType){
        ReportContext reportContext = new ReportContext();
        switch (driverType){
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
        return reportContext;
    }

    private static void writeDataToFile(Object document, String filePath) throws IOException {
        try {
            File file = new File(filePath);

            FileOutputStream fos = new FileOutputStream(file);
            if (document instanceof XWPFDocument) {
                XWPFDocument doc = (XWPFDocument) document;
                doc.write(fos);
            } else if (document instanceof HSSFWorkbook) {
                HSSFWorkbook doc = (HSSFWorkbook) document;
                doc.write(fos);
            } else {
                throw new IOException();
            }
            fos.close();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

}
