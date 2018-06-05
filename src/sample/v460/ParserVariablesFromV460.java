package sample.v460;


import com.opencsv.bean.*;
import sample.Helpers.Helpers;
import sample.Report.ReportPanelSprTitle;
import sample.Report.ReportPanelTitle;
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

    public ArrayList<PointParam> parse() throws Exception {

        List<ResourceBean> resourceBeans = getBeansFromCsv(getFile());

        Map<String, ArrayList<ResourceBean>> points = getSrcPoints(resourceBeans);

        ArrayList<PointParam> pointParams = getPointParams(points);

        return pointParams;
    }

    public String getFile() {
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

        List<ResourceBean> resourceBeans = new ArrayList<>(cb.parse());
        reader.close();



        if(resourceBeans != null){ return resourceBeans; }
        return new ArrayList<>();
    }

    private Map<String, ArrayList<ResourceBean>> getSrcPoints(List<ResourceBean> resourceBeans){
        Map<String, ArrayList<ResourceBean>> points = new Hashtable<>();

        Hashtable<String, ArrayList<ResourceBean>> dictionary = new Hashtable<>();

        int oldCountPanelPoints = 0;
        ArrayList<ResourceBean> variablesInPoint = new ArrayList<>();
        for(ResourceBean resourceBean : resourceBeans){
            if(isIecVariable(resourceBean)){
                oldCountPanelPoints = points.size();
                if(Helpers.isBeanSprecon850Driver(resourceBean)){
                    points.put(resourceBean.getPrefixConnection(),variablesInPoint);
                    if(dictionary.containsKey(resourceBean.getNetAddr())){
                        dictionary.get(resourceBean.getNetAddr()).add(resourceBean);
                    }else{
                        ArrayList<ResourceBean> beansInPoint = new ArrayList<>();
                        beansInPoint.add(resourceBean);
                        dictionary.put(resourceBean.getNetAddr(), beansInPoint);
                    }
                }else{
                    points.put(resourceBean.getPrefixTagname(),variablesInPoint);
                }
                if(points.size() > oldCountPanelPoints){
                    variablesInPoint = new ArrayList<>();
                }
                variablesInPoint.add(resourceBean);
            }
        }
        return dictionary;//points;
    }
    //private ArrayList<ResourceBean> setResourceBeansInDict(String key, Hashtable<String, ArrayList<ResourceBean>> dict)

    private boolean isIecVariable(ResourceBean resourceBean){
        return resourceBean.isIec850Variable() || resourceBean.isIec870Variable();
    }
    private ArrayList<PointParam> getPointParams(Map<String, ArrayList<ResourceBean>> points){

        ArrayList<PointParam> pointParams = new ArrayList<>();

        for(Map.Entry<String, ArrayList<ResourceBean>> entry: points.entrySet()){

            PointParam pointParam = new PointParam();
            ReportPanelTitle reportPanelTitle;
            DriverType driverType;
            ResourceBean resourceBean = entry.getValue().get(0);

            if(Helpers.isBeanSprecon850Driver(resourceBean)){
                reportPanelTitle = new ReportPanelSprTitle(resourceBean);
                driverType = DriverType.SPRECON850;
            }else{
                reportPanelTitle = new ReportPanelTitle(resourceBean);
                driverType = resourceBean.driverType();
            }
            pointParam.setReportPanelTitle(reportPanelTitle);
            pointParam.setDriverType(driverType);
            pointParam.setResourceBeans(entry.getValue());
            pointParams.add(pointParam);
        }

        return pointParams;

    }

}
