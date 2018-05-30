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
    private ArrayList<ResourceBean> beans;

    private static Map<String, ArrayList<ResourceBean>> panelPoints;



    public ParserVariablesFromV460(String file, ArrayList<ResourceBean> resourceBeans){
        fileCsv = file;
        beans = resourceBeans;
    }
    public ParserVariablesFromV460(String file){
        this(file, new ArrayList<>());
    }
    public ParserVariablesFromV460(){
        this(fileCsv, new ArrayList<>());
    }

    public static Map<String, ArrayList<ResourceBean>> parse(String file) throws Exception {

        //ParserVariablesFromV460 parserVariablesFromV460 = new ParserVariablesFromV460();

        Map<String, ArrayList<ResourceBean>> points = new Hashtable<>();

        Path path = Paths.get(file);

        CsvTransfer csvTransfer = new CsvTransfer();
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(ResourceBean.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(ResourceBean.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                .build();
        csvTransfer.setCsvList(cb.parse());
        reader.close();

        int oldCountPanelPoints = 0;
        ArrayList<ResourceBean> variablesInPoint = new ArrayList<>();

        for(ResourceBean resourceBean : csvTransfer.getCsvList()){

            if(resourceBean.isIec850Variable() || resourceBean.isIec870Variable()){
                oldCountPanelPoints = points.size();

                points.put(resourceBean.getPrefixTagname(),variablesInPoint);
                if(points.size() > oldCountPanelPoints){
                    variablesInPoint = new ArrayList<>();
                }

                switch (resourceBean.driverType()){
                    case IEC850:
                        Iec850VariableType iec850VariableType = (Iec850VariableType)resourceBean;
                        variablesInPoint.add(iec850VariableType);
                        break;
                    case SPRECON870: case IEC870:
                        Iec870VariableType iec870VariableType = (Iec870VariableType)resourceBean;
                        variablesInPoint.add(iec870VariableType);
                        break;
                    default:
                        break;
                }

            }
        }
        return points;
    }

    private void addVariable(ResourceBean resourceBean){
        beans.add(resourceBean);
    }

    public Map<String, ArrayList<ResourceBean>> getPanelPoints() {
        return panelPoints;
    }
}
