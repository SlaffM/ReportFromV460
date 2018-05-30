package sample;


import javafx.event.ActionEvent;
import sample.v460.AbstractBean;
import sample.v460.ParserVariablesFromV460;
import sample.Report.ReportCreator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;


public class Controller {
    public void createDocFile(ActionEvent actionEvent) throws Exception {

        Map<String, ArrayList<AbstractBean>> listPoints = new Hashtable<>();

        listPoints = ParserVariablesFromV460.parse("v460.txt");





        //ParserVariablesFromV460 parserVariablesFromV460 = ParserVariablesFromV460.parse("v460.txt");

        //ReportCreator.CreateDocFile(parserVariablesFromV460.getPanelPoints());

    }




}

