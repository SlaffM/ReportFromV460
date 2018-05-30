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

    private static Map<String, ArrayList<AbstractBean>> panelPoints;



    public ParserVariablesFromV460(String file, ArrayList<AbstractBean> abstractBeans){
        fileCsv = file;
        beans = abstractBeans;
    }
    public ParserVariablesFromV460(String file){
        this(file, new ArrayList<>());
    }
    public ParserVariablesFromV460(){
        this(fileCsv, new ArrayList<>());
    }

    public static Map<String, ArrayList<AbstractBean>> parse(String file) throws Exception {

        //ParserVariablesFromV460 parserVariablesFromV460 = new ParserVariablesFromV460();

        Map<String, ArrayList<AbstractBean>> points = new Hashtable<>();

        Path path = Paths.get(file);

        CsvTransfer csvTransfer = new CsvTransfer();
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(Iec870VariableType.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(Iec870VariableType.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                .build();
        csvTransfer.setCsvList(cb.parse());
        reader.close();

        int oldCountPanelPoints = 0;
        ArrayList<AbstractBean> variablesInPoint = new ArrayList<>();

        for(AbstractBean abstractBean : csvTransfer.getCsvList()){

            System.out.println(abstractBean.getPrefixTagname() +" - "+abstractBean.driverType());

            if(abstractBean.isIec850Variable() || abstractBean.isIec870Variable()){
                oldCountPanelPoints = points.size();

                points.put(abstractBean.getPrefixTagname(),variablesInPoint);
                if(points.size() > oldCountPanelPoints){
                    System.out.println(abstractBean.getPrefixTagname());
                    variablesInPoint = new ArrayList<>();
                }
                switch (abstractBean.driverType()) {
                    case IEC850:
                        Iec850VariableType iec850VariableType = (Iec850VariableType)abstractBean;
                        variablesInPoint.add(iec850VariableType);
                    case IEC870:
                    case SPRECON870:
                        Iec870VariableType iec870VariableType = (Iec870VariableType)abstractBean;
                        variablesInPoint.add(iec870VariableType);
                    default:
                        break;
                }


            }
        }
        return points;
    }



    private void addVariable(AbstractBean abstractBean){
        beans.add(abstractBean);
    }

    public Map<String, ArrayList<AbstractBean>> getPanelPoints() {
        return panelPoints;
    }
}
