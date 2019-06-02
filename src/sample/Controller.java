package sample;

import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import sample.Helpers.LogInfo;
import sample.Helpers.OpenFileFilter;
import sample.Report.Parsers.EnipObject;
import sample.Report.Parsers.ParserEnipJSON;
import sample.Report.Parsers.ParserVariablesV460;
import sample.Report.Parsers.ValidatorResourceBeans;
import sample.Report.ReportCreator;
import sample.v460.GrouperPoints;
import sample.v460.Point;
import sample.v460.ResourceBean;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML public Label lblPathTxt;
    @FXML public Label lblPathDoc;
    @FXML public Button btnGenerate;
    @FXML public Button btnLoadFileTxt;
    @FXML public Button btnSaveDocFile;
    @FXML public RadioButton rButExcel;
    @FXML public RadioButton rButWord;
    @FXML public Button btnReadJSON;
    @FXML public Button btnLoadPathToEnip;
    @FXML public Label lblPathEnip;
    @FXML public TextArea txtLog;
    @FXML public ComboBox<GrouperPoints> selectTypeGroup = new ComboBox<>();
    @FXML public ProgressBar progress = new ProgressBar();
    @FXML public ListView listLog;

    private File txtFile;
    private String extensionTxt = ".txt";
    private String extensionExcel = ".xls";
    private String extensionWord = ".docx";
    private String postfixNewFile = "_new";
    private String dirOfEnip = new File("./enips").getAbsolutePath();
    private String logLine = "";
    private Stage stage;

    @FXML public void createFile(ActionEvent actionEvent) throws Exception {

        ReportCreator reportCreator = new ReportCreator.ReportCreatorBuilder()
                .withPathV460Variables(txtFile.getAbsolutePath())
                .withPathEnipConfigurations(dirOfEnip)
                .withGrouperPoints(selectTypeGroup.getSelectionModel().getSelectedItem())
                .withSelectedExtension(rButExcel.isSelected())
                .withPathSavedFile(getlblPathDoc())
                .build();

        reportCreator.createReport();
/*

        EnipObject.clearAllEnips();
        Point.clearPoints();
        //for(File file: txtFile){

            List<ResourceBean> resourceBeans =
                    new ValidatorResourceBeans(txtFile.getAbsolutePath(), dirOfEnip)
                    .getReadyBeans();

            Point.buildPoints(
                    resourceBeans,
                    selectTypeGroup.getSelectionModel().getSelectedItem()
            );

            if (rButExcel.selectedProperty().getValue()){
                ReportCreator.CreateXlsFile(Point.getAllPoints(), getlblPathDoc());
            }else{
                ReportCreator.CreateDocFile(Point.getAllPoints(), getlblPathDoc());
            }

        //}
*/


    }


    private String getlblPathDoc(){
        return lblPathDoc.textProperty().getValue();
    }

    private void setLblText(Label lbl, String text){
        lbl.textProperty().setValue(text);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }


    @FXML public void btnLoadTxtFileClick(ActionEvent event) throws InvocationTargetException, InterruptedException {
        FileChooser fileopen = new FileChooser();
        fileopen.setInitialDirectory(new File("."));
        //fileopen.setSelectedExtensionFilter(new OpenFileFilter());
        fileopen.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT files", "*.txt"));
/*

        fileopen.setSelectedExtensionFilter(new OpenFileFilter(extensionTxt, "Text files"));
*/
        txtFile = fileopen.showOpenDialog(stage.getScene().getWindow());
        setLblText(lblPathTxt, txtFile.getAbsolutePath());
        setLblText(lblPathEnip, dirOfEnip);
        actualizingStateFormatFile();



        /*int ret = fileopen.showOpenDialog(null);

        if (ret == FileChooser.APPROVE_OPTION) {
            txtFile = fileopen.getSelectedFile();
            //txtFile = fileopen.getSelectedFiles();
            //if(txtFile != null || txtFile.length == 0){
                setLblText(lblPathTxt, txtFile.getAbsolutePath());
                setLblText(lblPathEnip, dirOfEnip.getAbsolutePath());
                actualizingStateFormatFile();
            //}

        }*/
/*

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        */
/*FileDialog fileDialog = new FileDialog(new Frame());
        fileDialog.setVisible(true);*//*


// Set back the property to file chooser.


/*
        EventQueue.invokeAndWait(() -> {
            *//*String folder = System.getProperty("user.dir");
            JFileChooser fc = new JFileChooser(folder);
            result = fc.showOpenDialog(null);*//*

            String folder = System.getProperty("user.dir");
            JFileChooser fileopen = new JFileChooser(folder);
            //fileopen.setCurrentDirectory(new File("."));
            fileopen.setFileFilter(new OpenFileFilter(extensionTxt, "Text files"));

            int ret = fileopen.showOpenDialog(null);



        });*/

        /*if (ret == JFileChooser.APPROVE_OPTION) {
            txtFile = ret.getSelectedFile();
            //txtFile = fileopen.getSelectedFiles();
            //if(txtFile != null || txtFile.length == 0){
            setLblText(lblPathTxt, txtFile.getAbsolutePath());
            setLblText(lblPathEnip, dirOfEnip.getAbsolutePath());
            actualizingStateFormatFile();
            //}

        }*/
    }


    @FXML public void rBtnExcelClicked(ActionEvent event){
        if (rButExcel.selectedProperty().getValue()) {
            rButWord.selectedProperty().setValue(false);
            actualizingStateFormatFile();
        }
    }

    @FXML public void rBtnWordClicked(ActionEvent event){
        if (rButWord.selectedProperty().getValue()) {
            rButExcel.selectedProperty().setValue(false);
            actualizingStateFormatFile();
        }
    }

    private void actualizingStateFormatFile(){
        if (rButExcel.selectedProperty().getValue()){
            setLblText(lblPathDoc, getChangedFileName(txtFile, extensionExcel));
        }else{
            setLblText(lblPathDoc, getChangedFileName(txtFile, extensionWord));
        }
    }

    private String getChangedFileName(File fileName, String replacement){
        if (!(txtFile == null) && !lblPathTxt.getText().isEmpty()) {
            if (fileName.getName().contains(extensionTxt)){
                return fileName.getAbsolutePath().replace(extensionTxt, postfixNewFile + replacement);
            }else{
                return fileName.getAbsolutePath() + replacement;
            }
        }
        return "";
    }

    @FXML public void btnSaveDocFileClick(ActionEvent event){
        /*File file;
        JFileChooser filesave = new JFileChooser();
        filesave.setCurrentDirectory(new File("."));
        OpenFileFilter wordFilter = new OpenFileFilter(extensionWord, "Microsoft Word files (*.docx)");
        OpenFileFilter excelFilter = new OpenFileFilter(extensionExcel, "Microsoft Excel files (*.xls)");
*/
        FileChooser fileopen = new FileChooser();
        fileopen.setInitialDirectory(new File("."));
        //fileopen.setSelectedExtensionFilter(new OpenFileFilter());

        FileChooser.ExtensionFilter wordFilter = new FileChooser.ExtensionFilter(
                "Microsoft Word files (*.docx)",
                extensionWord);
        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter(
                "Microsoft Excel files (*.xls)",
                extensionExcel);

        String curExtension;
        if (rButExcel.selectedProperty().getValue()){
            fileopen.getExtensionFilters().addAll(excelFilter);
            curExtension = extensionExcel;
        }else{
            fileopen.getExtensionFilters().addAll(wordFilter);
            curExtension = extensionWord;
        }

        File selectedFile = fileopen.showSaveDialog(stage.getScene().getWindow());

        /*int ret = filesave.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            file = filesave.getSelectedFile();*/
            setLblText(lblPathDoc, getChangedFileName(selectedFile, curExtension));
        //}*/


        /*
        String curExtension;

        if (rButExcel.selectedProperty().getValue()){
            fileopen.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"));
            filesave.setFileFilter(excelFilter);
            curExtension = extensionExcel;
        }else{
            fileopen.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"));
            filesave.setFileFilter(wordFilter);
            curExtension = extensionWord;
        }

        int ret = filesave.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            file = filesave.getSelectedFile();
            setLblText(lblPathDoc, getChangedFileName(file, curExtension));
        }*/
    }

    public void btnLoadPathToEnipClick(ActionEvent event) {


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File dir = directoryChooser.showDialog(stage.getScene().getWindow());
        if (dir != null){
            dirOfEnip = dir.getAbsolutePath();
            setLblText(lblPathEnip, dirOfEnip);
        }

        /*
        FileChooser fileopen = new FileChooser();
        fileopen.setInitialDirectory(new File("."));

        File selectedFile = fileopen.showOpenDialog(stage.getScene().getWindow());
        if (selectedFile.isDirectory()){
            dirOfEnip = selectedFile;
            setLblText(lblPathEnip, dirOfEnip.getAbsolutePath());
        }*/

        /*JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileopen.setAcceptAllFileFilterUsed(false);

        int ret = fileopen.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            dirOfEnip = fileopen.getSelectedFile();
            setLblText(lblPathEnip, dirOfEnip.getAbsolutePath());
        }
*/


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //txtLog.textProperty().bind(LogInfo.logDataProperty());
        listLog.itemsProperty().bind(LogInfo.logDataProperty());

        LogInfo.logDataProperty().addListener((observableValue, observableList, t1) ->
                listLog.scrollTo(LogInfo.logDataProperty().getSize()));

        selectTypeGroup.getItems().addAll(GrouperPoints.values());
        selectTypeGroup.getSelectionModel().select(GrouperPoints.GROUP_BY_NETADDR);
    }

}

