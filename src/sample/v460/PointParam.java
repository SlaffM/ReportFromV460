package sample.v460;

import sample.Helpers.Helpers;
import sample.Helpers.LogInfo;
import sample.Report.Parsers.EnipObject;
import sample.Report.ReportPanelTitle.ReportPanelSprTitle;
import sample.Report.ReportPanelTitle.ReportPanelTitle;

import java.util.*;

public class PointParam {

    private ReportPanelTitle reportPanelTitle;
    private DriverType driverType;
    private ArrayList<ResourceBean> resourceBeans;
    private ArrayList<EnipObject> enipObjects;
    private int netAddr;

    private static Map<Integer, PointParam> allPoints;

    private PointParam(Builder builder) {
        setResourceBeans(builder.resourceBeans);
        setEnipObjects(builder.enipObjects);

        addNetAddr();
        addDriverType();
        addReportPanelTitle();
        addCoefficientTransform();


        if (!hasPoint()){
            allPoints.put(getNetAddr(), this);
        }else{
            LogInfo.setErrorData("Устройство уже есть в базе: \n" +
                    this.toString() + "\n" +
                    new ArrayStoreException().getMessage());
        }
    }

    private boolean hasPoint(){
        for (PointParam pointParam : getAllPoints()){
            if (pointParam.equals(this) && pointParam.hashCode() == this.hashCode()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "PointParam{" +
                ", driverType=" + driverType +
                ", resourceBeansCount=" + resourceBeans.size() +
                ", netAddr=" + netAddr +
                '}';
    }


    public static ArrayList<PointParam> getAllPoints(){
        if (allPoints == null){
            allPoints = new HashMap<>();
        }
        return new ArrayList<>(allPoints.values());
    }

    public static int getPointsCount(){
        return getAllPoints().size();
    }

    private void addNetAddr(){
        if(!(resourceBeans.get(0) == null)){
            String addr = resourceBeans.get(0).getNetAddr();
            if (Helpers.tryParseInt(addr)) setNetAddr(Integer.parseInt(addr));
        }
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
                    for (EnipObject enipObject : enipObjects) {
                        if (isEnipObjectCorrect(resourceBean, enipObject)) {
                            if (resourceBean.isVariableU()) {
                                resourceBean.setCoefficientTransform(enipObject.getVoltageCoefficient() + "/1");
                                break;
                            } else if (resourceBean.isVariableI()) {
                                try {
                                    int coef = Integer.parseInt(enipObject.getCurrentCoefficient());
                                    if (coef < 400) {
                                        resourceBean.setCoefficientTransform(String.format("%s/5", coef * 5));
                                    } else {
                                        resourceBean.setCoefficientTransform(String.format("%s/1", coef));
                                    }
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }else{
                    resourceBean.setDefaultCoefficientTransform();
                }
            }
        }
    }

    private boolean isEnipObjectCorrect(ResourceBean resourceBean, EnipObject enipObject){
        return resourceBean.getNetAddr().equals(enipObject.getNetAddress());
    }

    public static PointParam getPointByNetAddr(int netAddr){
        for(PointParam pointParam: getAllPoints()){
            if (pointParam.getNetAddr() == netAddr){
                return pointParam;
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

    public static ArrayList<PointParam> buildPoints(ArrayList<ResourceBean> resourceBeans,
                                                    ArrayList<EnipObject> enipObjects){

        Hashtable<String, ArrayList<ResourceBean>> points = writeBeansToPointsByNetAddr(resourceBeans);
        for(Map.Entry<String, ArrayList<ResourceBean>> entry: points.entrySet()){
            new PointParam.Builder()
                    .enipObjects(enipObjects)
                    .resourceBeans(entry.getValue())
                    .build();
        }
        return getAllPoints();
    }

    public static Hashtable<String, ArrayList<ResourceBean>> writeBeansToPointsByNetAddr(List<ResourceBean> resourceBeans){
        Hashtable<String, ArrayList<ResourceBean>> dictionary = new Hashtable<>();

        int oldCountPanelPoints = 0;
        ArrayList<ResourceBean> variablesInPoint = new ArrayList<>();
        for(ResourceBean resourceBean: resourceBeans){
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
    }

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

    public ArrayList<ResourceBean> getResourceBeans() {
        return resourceBeans;
    }

    private void setResourceBeans(ArrayList<ResourceBean> resourceBeans) {
        Collections.sort(resourceBeans);
        this.resourceBeans = resourceBeans;
    }

    public int getNetAddr() {
        return netAddr;
    }

    public void setNetAddr(int netAddr) {
        this.netAddr = netAddr;
    }

    private boolean isSpreconTable(){
        return getDriverType().equals(DriverType.SPRECON850) || getDriverType().equals(DriverType.SPRECON870);
    }

    public static final class Builder {
        private ArrayList<ResourceBean> resourceBeans;
        private ArrayList<EnipObject> enipObjects;

        public Builder(){}

        public Builder resourceBeans(ArrayList<ResourceBean> val) {
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

        public PointParam build() {
            return new PointParam(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointParam that = (PointParam) o;
        return netAddr == that.netAddr && driverType == that.driverType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverType, netAddr);
    }
}
