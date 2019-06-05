package sample.Report.Formats;

public class FileFactory {
    public static ExtensionFormat extract(DocumentFile file) {
        String type = file.getExtension();
        if (type.equals("xls")) {
            return new ExcelDocument();
        }else {
            return new WordDocument();
        }
    }
}
