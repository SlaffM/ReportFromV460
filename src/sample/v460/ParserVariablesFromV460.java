package sample.v460;


import com.opencsv.bean.*;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParserVariablesFromV460 {

    private static String fileCsv;
    private ArrayList<AbstractBean> beans;

    private static Map<String, ArrayList<Iec870Variable>> panelPoints;



    public ParserVariablesFromV460(String file, ArrayList<AbstractBean> iec870Variables){
        fileCsv = file;
        beans = iec870Variables;
        panelPoints = new Hashtable<>();
    }
    public ParserVariablesFromV460(String file){
        this(file, new ArrayList<>());
    }
    public ParserVariablesFromV460(){
        this(fileCsv, new ArrayList<>());
    }

    public static ParserVariablesFromV460 parse(String file) throws Exception {

        ParserVariablesFromV460 parserVariablesFromV460 = new ParserVariablesFromV460();

        Path path = Paths.get(file);

        CsvTransfer csvTransfer = new CsvTransfer();
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(Iec870Variable.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(Iec870Variable.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                .build();
        csvTransfer.setCsvList(cb.parse());
        reader.close();

        int oldCountPanelPoints = 0;
        ArrayList<Iec870Variable> variablesInPoint = new ArrayList<>();

        for(AbstractBean abstractBean : csvTransfer.getCsvList()){

            parserVariablesFromV460.addVariable(abstractBean);
            //Iec870Variable iec870Variable = (Iec870Variable) abstractBean;

            AbstractBean bean = FactoryIecType

            oldCountPanelPoints = panelPoints.size();

            panelPoints.put(iec870Variable.getPrefixTagname(),variablesInPoint);
            if(panelPoints.size() > oldCountPanelPoints){
                System.out.println(iec870Variable.getDevice());
                variablesInPoint = new ArrayList<>();
            }
            variablesInPoint.add(iec870Variable);
        }
        return parserVariablesFromV460;
    }

    private void addVariable(AbstractBean abstractBean){
        beans.add(abstractBean);
    }

    public Map<String, ArrayList<Iec870Variable>> getPanelPoints() {
        return panelPoints;
    }
}
