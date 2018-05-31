package sample;

import javafx.event.ActionEvent;
import sample.Report.ReportCreator;
import sample.v460.PointParam;
import sample.v460.ParserVariablesFromV460;

import java.util.ArrayList;

public class Controller {

    public void createDocFile(ActionEvent actionEvent) throws Exception {
        ArrayList<PointParam> listPointParams = ParserVariablesFromV460.parse("v460.txt");
        ReportCreator.CreateDocFile(listPointParams);
    }
}

