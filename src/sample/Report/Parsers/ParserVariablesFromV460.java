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

    public ParserVariablesFromV460(String file){
        this.file = file;
    }

    public ArrayList<PointParam> buildPoints() throws Exception {
        List<ResourceBean> resourceBeans = getBeansFromCsv(getFile());
        Map<String, ArrayList<ResourceBean>> points = getSrcPoints(resourceBeans);
        return getPointsTable(points);
    }

    private String getFile() {
        return file;
    }

    private List<ResourceBean> getBeansFromCsv(String file) throws IOException {

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
        reader.close();

        return resourceBeans != null ? resourceBeans : new ArrayList<>();
    }

    private Map<String, ArrayList<ResourceBean>> getSrcPoints(List<ResourceBean> resourceBeans){
        Hashtable<String, ArrayList<ResourceBean>> dictionary = new Hashtable<>();

        int oldCountPanelPoints = 0;
        ArrayList<ResourceBean> variablesInPoint = new ArrayList<>();
        for(ResourceBean resourceBean : resourceBeans){
            resourceBean.setLodicDriverType();
            if(isIecVariable(resourceBean)){
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
    }

    private boolean isIecVariable(ResourceBean resourceBean){
        return resourceBean.isIec850Variable() || resourceBean.isIec870Variable();
    }
    private ArrayList<PointParam> getPointsTable(Map<String, ArrayList<ResourceBean>> points){

        ArrayList<PointParam> pointParams = new ArrayList<>();

        for(Map.Entry<String, ArrayList<ResourceBean>> entry: points.entrySet()){

            ResourceBean resourceBean = entry.getValue().get(0);
            PointParam pointParam = new PointParam.Builder()
                    .driverType(resourceBean.getLodicDriverType())
                    .reportPanelTitle(resourceBean)
                    .resourceBeans(entry.getValue())
                    .build();
            pointParams.add(pointParam);
        }
        return pointParams;
    }

}
