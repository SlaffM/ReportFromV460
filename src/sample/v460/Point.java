package sample.v460;

import sample.Helpers.LogInfo;
import sample.Report.Parsers.EnipObject;
import sample.Report.ReportPanelTitle.ReportPanelSprTitle;
import sample.Report.ReportPanelTitle.ReportPanelTitle;

import java.util.*;

public class Point {

    private ReportPanelTitle reportPanelTitle;
    private DriverType driverType;
    private List<ResourceBean> resourceBeans;
    private String grouppingParameter;
    private GrouperPoints grouperPoints;

    private static Map<String, Point> allPoints;

    private Point(Builder builder) {
        setResourceBeans(builder.resourceBeans);
        setGrouperPoints(builder.grouperPoints);

        addGrouppingParameter();
        addDriverType();
        addReportPanelTitle();

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
        ResourceBean typeBean = resourceBeans.get(0);
        switch (getGrouperPoints()){
            case GROUP_BY_PANEL:
                setGrouppingParameter(typeBean.getPanelLocation());
                break;
            case GROUP_BY_NETADDR:
            default:
                setGrouppingParameter(typeBean.getNetAddr());
        }
    }

    private void addReportPanelTitle(){
        setReportPanelTitle(resourceBeans.get(0));
    }

    private void addDriverType(){
        setDriverType(resourceBeans.get(0).getDriverType());
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
                                               GrouperPoints grouperPoints){

        DistributerToPoints.buildPoints(resourceBeans, grouperPoints).forEach(resourceBeans1 ->
                        new Builder()
                                .resourceBeans(resourceBeans1)
                                .grouperParameter(grouperPoints)
                                .build()
                );


        LogInfo.setLogDataWithTitle(
                "Количество устройств для создания протокола",
                String.valueOf(Point.getPointsCount()));
        return getAllPoints();
    }
/*
    public void setEnipObjects(ArrayList<EnipObject> enipObjects) {
        this.enipObjects = enipObjects;
    }*/

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
        //private ArrayList<EnipObject> enipObjects;
        private GrouperPoints grouperPoints;

        public Builder(){}

        public Builder resourceBeans(List<ResourceBean> val) {
            resourceBeans = val;
            return this;
        }
/*
        public Builder enipObjects(ArrayList<EnipObject> val) {
            if (val == null){
                val = new ArrayList<EnipObject>();
            }
            enipObjects = val;
            return this;
        }*/
        
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
