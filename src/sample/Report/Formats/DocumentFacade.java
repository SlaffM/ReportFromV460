package sample.Report.Formats;

import java.io.File;

public class DocumentFacade {

    public void createFile(String fileName, String format){
        DocumentFile documentFile = new DocumentFile(fileName);
        ExtensionFormat sourceTxtFile = FileFactory.extract(documentFile);

        ExtensionFormat dstFile;

        if (format.equals("xls")){
            dstFile = new ExcelDocument();
        }else {
            dstFile = new WordDocument();
        }

        dstFile.writeDocument();

    }

}
