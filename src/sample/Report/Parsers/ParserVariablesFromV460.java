package sample.Report.Parsers;


import com.opencsv.bean.*;
import sample.v460.PointParam;
import sample.v460.ResourceBean;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParserVariablesFromV460 {

    private String file;
    private ArrayList<EnipObject> enipObjects;

    public ParserVariablesFromV460(String file) {
        this(file, new ArrayList<EnipObject>());
    }

    public ParserVariablesFromV460(String file, ArrayList<EnipObject> enipObjects){
        this.file = file;
        this.enipObjects = enipObjects;
    }

    /*public ArrayList<PointParam> buildPoints() throws Exception {
        List<ResourceBean> resourceBeans = getBeansFromCsv(getFile());
        Map<String, ArrayList<ResourceBean>> points = PointParam.distributeBeansToPoints(resourceBeans);
        //getMapPointsByDeviceType(resourceBeans);
        return getReadyPoints(points);
    }*/

    private String getFile() {
        return file;
    }

    public ArrayList getBeansFromCsv() throws IOException {

        Path path = Paths.get(file);
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(ResourceBean.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(ResourceBean.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                .build();

        ArrayList resourceBeans = new ArrayList<>(cb.parse());

        ResourceBean.validateDriverType(resourceBeans);

        reader.close();

        return resourceBeans;
    }


/*
    private Map<String, ArrayList<ResourceBean>> getMapPointsByDeviceType(List<ResourceBean> resourceBeans){
        Hashtable<String, ArrayList<ResourceBean>> dictionary = new Hashtable<>();

        int oldCountPanelPoints = 0;
        ArrayList<ResourceBean> variablesInPoint = new ArrayList<>();
        for(ResourceBean resourceBean : resourceBeans){
            if(resourceBean.isIecVariable()){
                oldCountPanelPoints = dictionary.size();
                if (dictionary.containsKey(resourceBean.getNetAddr())) {
                    dictionary.get(resourceBean.getNetAddr()).add(resourceBean);
                } else {
                    ArrayList<ResourceBean> beansInPoint = new ArrayList<>();
                    beansInPoint.add(resourceBean);
                    dictionary.put(resourceBean.getNetAddr(), beansInPoint);
                }
                if (dictionary.size() > oldCountPanelPoints) { variablesInPoint = new ArrayList<>(); }
                variablesInPoint.add(resourceBean);
            }
        }
        return dictionary;
    }*/
/*

    private ArrayList<PointParam> getReadyPoints(Map<String, ArrayList<ResourceBean>> points){

        ArrayList<PointParam> pointParams = new ArrayList<>();

        //for(Map.Entry<String, ArrayList<ResourceBean>> entry: points.entrySet()){
            PointParam pointParam = new PointParam.Builder()
                    .enipObjects(enipObjects)
                    .resourceBeans(entry.getValue())
                    .build();
            pointParams.add(pointParam);
        }
        //return pointParams;
    }
*/

}
