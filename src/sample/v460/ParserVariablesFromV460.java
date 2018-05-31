package sample.v460;


import com.opencsv.bean.*;
import sample.Helpers.Helpers;
import sample.Report.ReportPanelTitle;


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

    public static ArrayList<PointParam> parse(String file) throws Exception {

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
                variablesInPoint.add(resourceBean);
            }
        }

        ArrayList<PointParam> pointParams = new ArrayList<>();

        for(Map.Entry<String, ArrayList<ResourceBean>> entry: points.entrySet()){

            PointParam pointParam = new PointParam();
            ReportPanelTitle reportPanelTitle = new ReportPanelTitle(entry.getKey());
            DriverType driverType = entry.getValue().get(0).driverType();
            if(Helpers.isBeanSprecon850Driver(entry.getValue().get(0)))
            {
                driverType = DriverType.SPRECON850;
            }
            pointParam.setReportPanelTitle(reportPanelTitle);
            pointParam.setDriverType(driverType);
            pointParam.setResourceBeans(entry.getValue());
            pointParams.add(pointParam);
        }

        return pointParams;
    }

    private void addVariable(ResourceBean resourceBean){
        beans.add(resourceBean);
    }

    public Map<String, ArrayList<ResourceBean>> getPanelPoints() {
        return panelPoints;
    }
}
