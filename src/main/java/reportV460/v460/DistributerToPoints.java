package reportV460.v460;

import reportV460.Helpers.LogInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;



public class DistributerToPoints {

    public static Map<Integer, List<ResourceBean>> getPointsByNetAddr(List<ResourceBean> resourceBeans){
        return resourceBeans.stream()
                .filter(ResourceBean::isIecVariable)
                .collect(Collectors.groupingBy(ResourceBean::getNetAddrInt));
    }

    public static Map<Integer, List<ResourceBean>> getPointsByPanel(List<ResourceBean> resourceBeans){
        return resourceBeans.stream()
                .filter(ResourceBean::isIecVariable)
                .collect(Collectors.groupingBy(ResourceBean::getPanelLocationInt));
    }

    public static ArrayList<List<ResourceBean>> buildPoints(List<ResourceBean> resourceBeans, GrouperPoints grouperPoints){
        Map<Integer, List<ResourceBean>> points = new HashMap<>();
        switch (grouperPoints){
            case GROUP_BY_NETADDR:
                points = getPointsByNetAddr(resourceBeans);
                break;
            case GROUP_BY_PANEL:
                points = getPointsByPanel(resourceBeans);
                break;
        }

        //Map<Integer, List<ResourceBean>> sorted= new HashMap();
        try{
            points.forEach((s, resourceBeans1) -> Collections.sort(resourceBeans1));
            /*sorted = points
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1,e2) -> e2, LinkedHashMap::new));*/

        }catch (Exception e){
            LogInfo.setErrorData(e.getMessage());
        }

        return new ArrayList<>(points.values());
        //return new ArrayList<>(sorted.values());
    }





}
