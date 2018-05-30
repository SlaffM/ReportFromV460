package sample;


import javafx.event.ActionEvent;
import sample.v460.ResourceBean;
import sample.v460.ParserVariablesFromV460;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class Controller {
    public void createDocFile(ActionEvent actionEvent) throws Exception {

        Map<String, ArrayList<ResourceBean>> listPoints = new Hashtable<>();

        listPoints = ParserVariablesFromV460.parse("v460.txt");





        //ParserVariablesFromV460 parserVariablesFromV460 = ParserVariablesFromV460.parse("v460.txt");

        //ReportCreator.CreateDocFile(parserVariablesFromV460.getPanelPoints());

    }




}

