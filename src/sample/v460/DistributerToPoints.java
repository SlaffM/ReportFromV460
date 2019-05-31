package sample.v460;

import sample.Helpers.LogInfo;
import sample.Report.Parsers.EnipObject;

import java.util.*;
import java.util.stream.Collectors;

public class DistributerToPoints {

    public static Map<String, List<ResourceBean>> getPointsByNetAddr(List<ResourceBean> resourceBeans){
        return resourceBeans.stream()
                .filter(ResourceBean::isIecVariable)
                .collect(Collectors.groupingBy(ResourceBean::getNetAddr));
    }

    public static Map<String, List<ResourceBean>> getPointsByPanel(List<ResourceBean> resourceBeans){
        return resourceBeans.stream()
                .filter(ResourceBean::isIecVariable)
                .collect(Collectors.groupingBy(ResourceBean::getPanelLocation));
    }

    public static Map<String, List<ResourceBean>> getPointsByLine(List<ResourceBean> resourceBeans){
        return resourceBeans.stream()
                .filter(ResourceBean::isIecVariable)
                .collect(Collectors.groupingBy(ResourceBean::getConnectionTitle));
    }

    public static ArrayList<List<ResourceBean>> buildPoints(List<ResourceBean> resourceBeans, GrouperPoints grouperPoints){
        Map<String, List<ResourceBean>> points = new HashMap<>();
        switch (grouperPoints){
            case GROUP_BY_NETADDR:
                points = getPointsByNetAddr(resourceBeans);
                break;
            case GROUP_BY_PANEL:
                points = getPointsByPanel(resourceBeans);
                break;
        }

        /*for (Map.Entry<String, List<ResourceBean>> point: points.entrySet()){
            //List<ResourceBean> beans = point.getValue();
            try {
                Collections.sort(point.getValue());
            }catch (Exception e){
                LogInfo.setErrorData(point.getKey() + "\t" + e.getMessage());
            }finally {
                return new ArrayList<>(points.values());
            }

        }*/


        try{
            points.forEach((s, resourceBeans1) -> Collections.sort(resourceBeans1));
        }catch (Exception e){
            LogInfo.setErrorData(e.getMessage());
        }


        return new ArrayList<>(points.values());
    }





}
