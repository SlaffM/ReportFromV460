package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ParallelCamera;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import sample.Helpers.OpenFileFilter;
import sample.Report.Parsers.EnipObject;
import sample.Report.Parsers.ParserJSON;
import sample.Report.ReportCreator;
import sample.v460.PointParam;
import sample.Report.Parsers.ParserVariablesFromV460;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Controller {
    @FXML public Label lblPathTxt;
    @FXML public Label lblPathDoc;
    @FXML public Button btnGenerate;
    @FXML public Button btnLoadFileTxt;
    @FXML public Button btnSaveDocFile;
    @FXML public RadioButton rButExcel;
    @FXML public RadioButton rButWord;
    @FXML public Button btnReadJSON;

    private File txtFile;
    private String extensionTxt = ".txt";
    private String extensionExcel = ".xls";
    private String extensionWord = ".docx";
    private String postfixNewFile = "_new";


    @FXML public void createFile(ActionEvent actionEvent) throws Exception {

        ArrayList<PointParam> listPointParams = new ParserVariablesFromV460(lblPathTxt.textProperty().getValue()).buildPoints();

        if (rButExcel.selectedProperty().getValue()){
            ReportCreator.CreateXlsFile(listPointParams, lblPathDoc.textProperty().getValue());
        }else{
            ReportCreator.CreateDocFile(listPointParams, lblPathDoc.textProperty().getValue());
        }
    }

    @FXML public void btnLoadTxtFileClick(ActionEvent event){
        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        //fileopen.addChoosableFileFilter(new OpenFileFilter(".txt", "Text files"));
        fileopen.setFileFilter(new OpenFileFilter(extensionTxt, "Text files"));

        int ret = fileopen.showOpenDialog(null);

        if (ret == JFileChooser.APPROVE_OPTION) {
            txtFile = fileopen.getSelectedFile();
            lblPathTxt.textProperty().setValue(txtFile.getAbsolutePath());
            actualizingStateFormatFile();
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
            lblPathDoc.textProperty().setValue(getChangedFileName(txtFile, extensionExcel));
        }else{
            lblPathDoc.textProperty().setValue(getChangedFileName(txtFile, extensionWord));
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
            lblPathDoc.textProperty().setValue(getChangedFileName(file, curExtension));
        }
    }

    @FXML public void readJSON(){

        File folder = new File("./enips");

        ArrayList<EnipObject> enipObjects = ParserJSON.getListOfEnips(folder);

        enipObjects.forEach(System.out::println);

        /*File file = new File("./enips/test.json");
        System.out.println(ParserJSON.getEnipFromJSON(file));

        file = new File("./enips/test2.json");
        System.out.println(ParserJSON.getEnipFromJSON(file));*/
    }



}

