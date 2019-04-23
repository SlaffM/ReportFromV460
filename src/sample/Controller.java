package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Report.ReportCreator;
import sample.v460.PointParam;
import sample.v460.ParserVariablesFromV460;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

public class Controller {
    @FXML public Label lblPathTxt;
    @FXML public Label lblPathDoc;
    @FXML public Button btnGenerate;
    @FXML public Button btnLoadFileTxt;
    @FXML public Button btnSaveDocFile;


    @FXML public void createDocFile(ActionEvent actionEvent) throws Exception {
        ArrayList<PointParam> listPointParams = new ParserVariablesFromV460(lblPathTxt.textProperty().getValue()).buildPoints();
        ReportCreator.CreateDocFile(listPointParams, lblPathDoc.textProperty().getValue());
    }

    @FXML public void btnLoadTxtFileClick(ActionEvent event){
        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            fileopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("text files", "txt");
            fileopen.setFileFilter(fileNameExtensionFilter);
            lblPathTxt.textProperty().setValue(file.getAbsolutePath());
        }
    }

    @FXML public void btnSaveDocFileClick(ActionEvent event){
        JFileChooser filesave = new JFileChooser();
        filesave.setCurrentDirectory(new File("."));
        int ret = filesave.showDialog(null, "Сохранить файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = filesave.getSelectedFile();
            filesave.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("word files", "docx");
            filesave.setFileFilter(fileNameExtensionFilter);
            lblPathDoc.textProperty().setValue(file.getAbsolutePath());
        }
    }

}

