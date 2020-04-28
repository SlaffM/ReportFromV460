package reportV460.Report.Formats;

import javafx.application.Platform;
import reportV460.Report.Parsers.EnipObject;
import reportV460.Report.Parsers.ValidatorResourceBeans;
import reportV460.v460.GrouperPoints;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.io.IOException;
import java.util.List;
import javafx.concurrent.Task;

public class CreatorPointsAndExtractToFormat {
    private String pathSavedFile;


    public CreatorPointsAndExtractToFormat(String pathSavedFile) {
        this.pathSavedFile = pathSavedFile;
    }

    public Task<Void> createFile() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Do your processing here

                updateProgress(0, 100);

                Platform.runLater(() -> {
                    ExtensionFormat selectedFormat = new FileFactory.FileFactoryBuilder()
                            .withDocumentFile(pathSavedFile)
                            .withPoints(Point.getAllPoints())
                            .build();
                    selectedFormat.writeDocument();
                });

                updateProgress(80, 100);

                return null;
            }
        };
    }


    /*public void createFile(){
        ExtensionFormat selectedFormat = new FileFactory.FileFactoryBuilder()
                .withDocumentFile(pathSavedFile)
                .withPoints(Point.getAllPoints())
                .build();
        selectedFormat.writeDocument();
    }*/

    public static final class DocumentFacadeBuilder {
        private String pathV460Variables;
        private String pathEnipConfigurations;
        private GrouperPoints grouperPoints;
        private String pathSavedFile;

        public DocumentFacadeBuilder() {
        }

        public DocumentFacadeBuilder withPathV460Variables(String pathV460Variables) {
            this.pathV460Variables = pathV460Variables;
            return this;
        }

        public DocumentFacadeBuilder withPathEnipConfigurations(String pathEnipConfigurations) {
            this.pathEnipConfigurations = pathEnipConfigurations;
            return this;
        }

        public DocumentFacadeBuilder withGrouperPoints(GrouperPoints grouperPoints) {
            this.grouperPoints = grouperPoints;
            return this;
        }

        public DocumentFacadeBuilder withPathSavedFile(String pathSavedFile) {
            this.pathSavedFile = pathSavedFile;
            return this;
        }



        public CreatorPointsAndExtractToFormat build() throws IOException {
            EnipObject.clearAllEnips();
            Point.clearPoints();

            List<ResourceBean> resourceBeans =
                    new ValidatorResourceBeans(pathV460Variables, pathEnipConfigurations).getReadyBeans();

            Point.buildPoints(resourceBeans, grouperPoints);

            return new CreatorPointsAndExtractToFormat(pathSavedFile);
        }
    }
}
