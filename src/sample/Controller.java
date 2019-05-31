package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.Helpers.LogInfo;
import sample.Helpers.OpenFileFilter;
import sample.Report.Parsers.EnipObject;
import sample.Report.Parsers.ParserEnipJSON;
import sample.Report.Parsers.ParserVariablesV460;
import sample.Report.ReportCreator;
import sample.v460.DistributerToPoints;
import sample.v460.GrouperPoints;
import sample.v460.Point;
import sample.v460.ResourceBean;

import javax.swing.*;
import java.io.File;
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

    private File txtFile;
    private String extensionTxt = ".txt";
    private String extensionExcel = ".xls";
    private String extensionWord = ".docx";
    private String postfixNewFile = "_new";
    private File dirOfEnip = new File("./enips");
    private String logLine = "";

    @FXML public void createFile(ActionEvent actionEvent) throws Exception {

        EnipObject.clearAllEnips();
        Point.clearPoints();

        ParserEnipJSON.getListOfEnips(dirOfEnip);
        String txtPath = lblPathTxt.textProperty().getValue();

        //for(File file: txtFile){

        List<ResourceBean> resourceBeans = new ParserVariablesV460(txtPath).getBeansFromCsv();

        ArrayList<Point> listPoints = Point.buildPoints(
                resourceBeans,
                EnipObject.getAllEnips(),
                selectTypeGroup.getSelectionModel().getSelectedItem()
        );

            /*ArrayList<Point> listPoints = Point.buildPoints(
                    resourceBeans,
                    EnipObject.getAllEnips(),
                    (GrouperPoints) selectTypeGroup.getSelectionModel().getSelectedItem());*/

            if (rButExcel.selectedProperty().getValue()){
                ReportCreator.CreateXlsFile(listPoints, getlblPathDoc());
            }else{
                ReportCreator.CreateDocFile(listPoints, getlblPathDoc());
            }

        //}


    }



    private String getlblPathDoc(){
        return lblPathDoc.textProperty().getValue();
    }

    private void setLblText(Label lbl, String text){
        lbl.textProperty().setValue(text);
    }


    @FXML public void btnLoadTxtFileClick(ActionEvent event){
        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        fileopen.setFileFilter(new OpenFileFilter(extensionTxt, "Text files"));

        int ret = fileopen.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            txtFile = fileopen.getSelectedFile();
            //txtFile = fileopen.getSelectedFiles();
            //if(txtFile != null || txtFile.length == 0){
                setLblText(lblPathTxt, txtFile.getAbsolutePath());
                setLblText(lblPathEnip, dirOfEnip.getAbsolutePath());
                actualizingStateFormatFile();
            //}

        }
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
            //lblPathDoc.textProperty().setValue(txtFile.getPath());
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
        File file;
        JFileChooser filesave = new JFileChooser();
        filesave.setCurrentDirectory(new File("."));
        OpenFileFilter wordFilter = new OpenFileFilter(extensionWord, "Microsoft Word files (*.docx)");
        OpenFileFilter excelFilter = new OpenFileFilter(extensionExcel, "Microsoft Excel files (*.xls)");

        String curExtension;

        if (rButExcel.selectedProperty().getValue()){
            filesave.setFileFilter(excelFilter);
            curExtension = extensionExcel;
        }else{
            filesave.setFileFilter(wordFilter);
            curExtension = extensionWord;
        }

        int ret = filesave.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            file = filesave.getSelectedFile();
            setLblText(lblPathDoc, getChangedFileName(file, curExtension));
        }
    }

    public void btnLoadPathToEnipClick(ActionEvent event) {
        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileopen.setAcceptAllFileFilterUsed(false);

        int ret = fileopen.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            dirOfEnip = fileopen.getSelectedFile();
            setLblText(lblPathEnip, dirOfEnip.getAbsolutePath());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtLog.textProperty().bind(LogInfo.logDataProperty());
        selectTypeGroup.getItems().addAll(GrouperPoints.values());
        selectTypeGroup.getSelectionModel().select(GrouperPoints.GROUP_BY_NETADDR);
    }

}

