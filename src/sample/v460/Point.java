package sample.v460;

import sample.Helpers.Helpers;
import sample.Helpers.LogInfo;
import sample.Report.Parsers.EnipObject;
import sample.Report.ReportPanelTitle.ReportPanelSprTitle;
import sample.Report.ReportPanelTitle.ReportPanelTitle;

import java.util.*;

public class Point {

    private ReportPanelTitle reportPanelTitle;
    private DriverType driverType;
    private List<ResourceBean> resourceBeans;
    private ArrayList<EnipObject> enipObjects;
    private String grouppingParameter;
    private GrouperPoints grouperPoints;

    private static Map<String, Point> allPoints;

    private Point(Builder builder) {
        setResourceBeans(builder.resourceBeans);
        setEnipObjects(builder.enipObjects);
        setGrouperPoints(builder.grouperPoints);

        addGrouppingParameter();
        addDriverType();
        addReportPanelTitle();
        addCoefficientTransform();

        if (!hasPoint()){
            allPoints.put(getGrouppingParameter(), this);
        }else{
            LogInfo.setErrorData("Устройство уже есть в базе: \n" +
                    this.toString() + "\n" +
                    new ArrayStoreException().getMessage());
        }
    }

    private boolean hasPoint(){
        for (Point point : getAllPoints()){
            if (point.equals(this) && point.hashCode() == this.hashCode()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Point{" +
                ", driverType=" + driverType +
                ", resourceBeansCount=" + resourceBeans.size() +
                ", grouppingParameter=" + grouppingParameter +
                '}';
    }


    public static ArrayList<Point> getAllPoints(){
        if (allPoints == null){
            allPoints = new HashMap<>();
        }
        return new ArrayList<>(allPoints.values());
    }

    public void setGrouperPoints(GrouperPoints grouperPoints) {
        this.grouperPoints = grouperPoints;
    }

    public static int getPointsCount(){
        return getAllPoints().size();
    }

    private void addGrouppingParameter(){
        String groupParam = "";
        ResourceBean typeBean = resourceBeans.get(0);
        switch (getGrouperPoints()){
            case GROUP_BY_NETADDR:
                groupParam = typeBean.getNetAddr();
                break;
            case GROUP_BY_PANEL:
                groupParam = typeBean.getPanelLocation();
                break;
            default:
                groupParam = typeBean.getNetAddr();
        }
        setGrouppingParameter(groupParam);

        /*if(!(resourceBeans.get(0) == null)){
            String addr = resourceBeans.get(0).getNetAddr();
            if (Helpers.tryParseInt(addr)) setGrouppingParameter(Integer.parseInt(addr));
        }*/
    }

    private void addReportPanelTitle(){
        setReportPanelTitle(resourceBeans.get(0));
    }

    private void addDriverType(){
        setDriverType(resourceBeans.get(0).getDriverType());
    }

    private void addCoefficientTransform() {
        for (ResourceBean resourceBean : resourceBeans) {
            if (resourceBean.isVariableTI()) {
                if (!enipObjects.isEmpty()) {
                    setCoefficientTransformWithEnip(resourceBean);
                }else{
                    resourceBean.setDefaultCoefficientTransform();
                }
            }
        }
    }

    private void setCoefficientTransformWithEnip(ResourceBean resourceBean){
        for (EnipObject enipObject : enipObjects) {
            if (isEnipObjectCorrect(resourceBean, enipObject)) {
                if (resourceBean.isVariableU()) {
                    resourceBean.setCoefficientTransform(enipObject.getVoltageCoefficient() + "/1");
                    break;
                } else if (resourceBean.isVariableI()){
                    try {
                        int coef = Integer.parseInt(enipObject.getCurrentCoefficient());
                        if (coef < 400) {
                            resourceBean.setCoefficientTransform(String.format("%s/5", coef * 5));
                        } else {
                            resourceBean.setCoefficientTransform(String.format("%s/1", coef));
                        }
                        break;
                    } catch (NumberFormatException e) {
                        LogInfo.setErrorData(resourceBean.getSignalName() +"\t" + e.getMessage());
                    }
                } else {
                    break;
                }
            }
        }
    }

    private boolean isEnipObjectCorrect(ResourceBean resourceBean, EnipObject enipObject){
        return resourceBean.getNetAddr().equals(enipObject.getNetAddress());
    }

    public static Point getPointByNetAddr(int netAddr){
        for(Point point : getAllPoints()){
            if (point.getGrouppingParameter().equals(String.valueOf(netAddr))){
                return point;
            }
        }
        return null;
    }

    public static void clearPoints(){
        if(allPoints == null){
            allPoints = new HashMap<>();
        }
        allPoints.clear();
    }

    public static ArrayList<Point> buildPoints(List<ResourceBean> resourceBeans,
                                               ArrayList<EnipObject> enipObjects,
                                               GrouperPoints grouperPoints){

        /*Hashtable<String, ArrayList<ResourceBean>> points = distributeBeansToPoints(
                resourceBeans,
                grouperPoints);*/
        /*ArrayList<List<ResourceBean>> points = DistributerToPoints.buildPoints(resourceBeans, grouperPoints);

        points.forEach(resourceBeans1 ->
                new Builder()
                .enipObjects(enipObjects)
                .resourceBeans(resourceBeans1)
                .grouperParameter(grouperPoints)
                .build()
        );*/

        DistributerToPoints.buildPoints(resourceBeans, grouperPoints).forEach(resourceBeans1 ->
                        new Builder()
                                .enipObjects(enipObjects)
                                .resourceBeans(resourceBeans1)
                                .grouperParameter(grouperPoints)
                                .build()
                );


        /*for(Map.Entry<String, ArrayList<ResourceBean>> entry: points.entrySet()){
            new Point.Builder()
                    .enipObjects(enipObjects)
                    .resourceBeans(entry.getValue())
                    .grouperParameter(grouperPoints)
                    .build();
        }*/

        LogInfo.setLogDataWithTitle(
                "Количество устройств для создания протокола",
                String.valueOf(Point.getPointsCount()));
        return getAllPoints();
    }

    /*public static Hashtable<String, ArrayList<ResourceBean>> distributeBeansToPoints(List<ResourceBean> resourceBeans,
                                                                                     GrouperPoints grouperPoints){
        Hashtable<String, ArrayList<ResourceBean>> dictionary = new Hashtable<>();

        int oldCountPanelPoints = 0;
        ArrayList<ResourceBean> variablesInPoint = new ArrayList<>();
        for(ResourceBean resourceBean: resourceBeans){
            if(resourceBean.isIecVariable()){
                String groupParameter = resourceBean.getGrouppingParameter();
                switch (grouperPoints){
                    case GROUP_BY_NETADDR:
                        groupParameter = resourceBean.getGrouppingParameter();
                        break;
                    case GROUP_BY_PANEL:
                        groupParameter = resourceBean.getConnectionTitle();
                        break;
                }

                oldCountPanelPoints = dictionary.size();
                if (dictionary.containsKey(groupParameter)) {
                    dictionary.get(groupParameter).add(resourceBean);
                } else {
                    ArrayList<ResourceBean> beansInPoint = new ArrayList<>();
                    beansInPoint.add(resourceBean);
                    dictionary.put(groupParameter, beansInPoint);
                }
                if (dictionary.size() > oldCountPanelPoints) { variablesInPoint = new ArrayList<>(); }
                variablesInPoint.add(resourceBean);
            }
        }
        return dictionary;
    }*/

    public void setEnipObjects(ArrayList<EnipObject> enipObjects) {
        this.enipObjects = enipObjects;
    }

    public ReportPanelTitle getReportPanelTitle() {
        return reportPanelTitle;
    }

    private void setReportPanelTitle(ResourceBean resourceBean) {
        this.reportPanelTitle = isSpreconTable() ? new ReportPanelSprTitle(resourceBean) : new ReportPanelTitle(resourceBean);
    }

    public DriverType getDriverType() {
        return driverType;
    }

    private void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public List<ResourceBean> getResourceBeans() {
        return resourceBeans;
    }

    public GrouperPoints getGrouperPoints() {
        return grouperPoints;
    }

    private void setResourceBeans(List<ResourceBean> resourceBeans) {
        this.resourceBeans = resourceBeans;
    }

    public String getGrouppingParameter() {
        return grouppingParameter;
    }

    public void setGrouppingParameter(String grouppingParameter) {
        this.grouppingParameter = grouppingParameter;
    }

    private boolean isSpreconTable(){
        return getDriverType().equals(DriverType.SPRECON850) || getDriverType().equals(DriverType.SPRECON870);
    }

    public static final class Builder {
        private List<ResourceBean> resourceBeans;
        private ArrayList<EnipObject> enipObjects;
        private GrouperPoints grouperPoints;

        public Builder(){}

        public Builder resourceBeans(List<ResourceBean> val) {
            resourceBeans = val;
            return this;
        }

        public Builder enipObjects(ArrayList<EnipObject> val) {
            if (val == null){
                val = new ArrayList<EnipObject>();
            }
            enipObjects = val;
            return this;
        }
        
        public Builder grouperParameter(GrouperPoints val){
            grouperPoints = val;
            return this;
        }

        public Point build() {
            return new Point(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return grouppingParameter == that.grouppingParameter && driverType == that.driverType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverType, grouppingParameter);
    }
}
