package reportV460.Report.Formats;

import reportV460.v460.Point;

import java.util.ArrayList;

public class FileFactory {

    public static final class FileFactoryBuilder {
        private ArrayList<Point> points;
        private DocumentFile documentFile;

        FileFactoryBuilder() { }

        public FileFactoryBuilder withPoints(ArrayList<Point> points) {
            this.points = points;
            return this;
        }

        public FileFactoryBuilder withDocumentFile(String documentFile) {
            this.documentFile = new DocumentFile(documentFile);
            return this;
        }

        public ExtensionFormat build() {

            String type = documentFile.getExtension();
            if (type.equals("xls")) {
                return new ExcelDocument(points, documentFile);
            }else {
                return new WordDocument(points, documentFile);
            }

        }
    }
}
