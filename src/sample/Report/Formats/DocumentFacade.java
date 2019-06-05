package sample.Report.Formats;

import sample.v460.Point;

import java.io.File;
import java.util.ArrayList;

public class DocumentFacade {

    public void createFile(ArrayList<Point> points, String fileName){
        DocumentFile documentFile = new DocumentFile(fileName);
        ExtensionFormat sourceTxtFile = FileFactory.extract(documentFile);

        dstFile.writeDocument();

    }

}
