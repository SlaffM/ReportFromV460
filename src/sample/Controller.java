package sample;


import javafx.event.ActionEvent;
import sample.v460.ParserVariablesFromV460;
import sample.Report.ReportCreator;


public class Controller {
    public void createDocFile(ActionEvent actionEvent) throws Exception {

        ParserVariablesFromV460 parserVariablesFromV460 = ParserVariablesFromV460.parse("v460.txt");

        ReportCreator.CreateDocFile(parserVariablesFromV460.getPanelPoints());

    }




}

