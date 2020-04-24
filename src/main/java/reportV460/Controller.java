package reportV460;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import reportV460.Helpers.LogInfo;
import reportV460.Helpers.Prefs;
import reportV460.Report.Formats.CreatorPointsAndExtractToFormat;
import reportV460.v460.GrouperPoints;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

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
    @FXML public ProgressBar progress;
    @FXML public ListView listLog;
    @FXML public TextField txtIp;
    @FXML public Label lblPathXml;
    @FXML public CheckBox chkResult;

    private File txtFile;
    private File xmlFile;
    private String extensionTxt = ".txt";
    private String extensionExcel = ".xls";
    private String extensionWord = ".docx";
    private String postfixNewFile = "_new";
    private String dirOfEnip = new File("./enips").getAbsolutePath();
    private String logLine = "";
    private Stage stage;

    @FXML public void createFile(ActionEvent actionEvent) throws Exception {

        Prefs.setPrefValue("IP", txtIp.textProperty().getValue());
        Prefs.setPrefValue("RESULT", getChkResultValue());



        CreatorPointsAndExtractToFormat creatorPointsAndExtractToFormat = new CreatorPointsAndExtractToFormat.DocumentFacadeBuilder()
                .withPathV460Variables(txtFile.getAbsolutePath())
                .withPathEnipConfigurations(dirOfEnip)
                .withGrouperPoints(selectTypeGroup.getSelectionModel().getSelectedItem())
                .withPathSavedFile(getlblPathDoc())
                .build();
        creatorPointsAndExtractToFormat.createFile();
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
    private String getChkResultValue(){
        return chkResult.selectedProperty().getValue() ? "+" : "";
    }

    @FXML public void btnLoadTxtFileClick(ActionEvent event) throws InvocationTargetException, InterruptedException {
        FileChooser fileopen = new FileChooser();
        if (txtFile != null)    { fileopen.setInitialDirectory(txtFile.getParentFile());}
        else                    { fileopen.setInitialDirectory(new File(".")); }

        fileopen.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT files", "*.txt"));
        txtFile = fileopen.showOpenDialog(stage.getScene().getWindow());
        if(txtFile != null) {
            setLblText(lblPathTxt, txtFile.getAbsolutePath());
            setLblText(lblPathEnip, dirOfEnip);
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
        FileChooser fileopen = new FileChooser();
        fileopen.setInitialDirectory(new File("."));

        String curExtension;
        if (rButExcel.selectedProperty().getValue()){
            new FileChooser.ExtensionFilter("Microsoft Excel files (*.xls)", extensionExcel);
            curExtension = extensionExcel;
        }else{
            new FileChooser.ExtensionFilter("Microsoft Word files (*.docx)", extensionWord);
            curExtension = extensionWord;
        }

        File selectedFile = fileopen.showSaveDialog(stage.getScene().getWindow());
        setLblText(lblPathDoc, getChangedFileName(selectedFile, curExtension));

    }

    public void btnLoadPathToEnipClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File dir = directoryChooser.showDialog(stage.getScene().getWindow());
        if (dir != null){
            dirOfEnip = dir.getAbsolutePath();
            setLblText(lblPathEnip, dirOfEnip);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {

        txtIp.textProperty().setValue("192.168.220.");

        listLog.itemsProperty().bind(LogInfo.logDataProperty());

        LogInfo.logDataProperty().addListener((observableValue, observableList, t1) ->
                listLog.scrollTo(LogInfo.logDataProperty().getSize()));

        selectTypeGroup.getItems().addAll(GrouperPoints.values());
        selectTypeGroup.getSelectionModel().select(GrouperPoints.GROUP_BY_NETADDR);


        Prefs.setPrefValue("PathProgramm", new File("." ).getAbsolutePath());

        setLblText(lblPathXml, Prefs.getPrefValue("PathProgramm") + "/template/variables.xml");
        setLblText(lblPathEnip, dirOfEnip);


    }

    public void btnLoadXmlFileClick(ActionEvent event) {
        FileChooser fileopen = new FileChooser();
        fileopen.setInitialDirectory(new File("."));
        fileopen.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xml"));
        xmlFile = fileopen.showOpenDialog(stage.getScene().getWindow());
        if (xmlFile != null) {
            setLblText(lblPathXml, xmlFile.getAbsolutePath());

            Prefs.setPrefValue("PathProgramm", lblPathXml.textProperty().getValue());
        }
    }
}

