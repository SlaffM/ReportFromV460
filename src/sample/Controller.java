package sample;


import javafx.event.ActionEvent;
import sample.Report.ReportCreator;
import sample.v460.PointParam;
import sample.v460.ResourceBean;
import sample.v460.ParserVariablesFromV460;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class Controller {
    public void createDocFile(ActionEvent actionEvent) throws Exception {

        ArrayList<PointParam> listPointParams = new ArrayList<>();

        listPointParams = ParserVariablesFromV460.parse("v460.txt");

        ReportCreator.CreateDocFile(listPointParams);





        //ParserVariablesFromV460 parserVariablesFromV460 = ParserVariablesFromV460.parse("v460.txt");

        //ReportCreator.CreateDocFile(parserVariablesFromV460.getPanelPoints());

    }




}

