package reportV460.v460;

import org.xml.sax.SAXException;
import reportV460.Helpers.LogInfo;
import reportV460.Report.ReportContext;
import reportV460.Report.ReportPanelTitle.ReportPanelSprTitle;
import reportV460.Report.ReportPanelTitle.ReportPanelTitle;
import reportV460.Report.Strategy.Iec850SprStrategy;
import reportV460.Report.Strategy.Iec850Strategy;
import reportV460.Report.Strategy.Iec870SprStrategy;
import reportV460.Report.Strategy.Iec870Strategy;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.*;
import java.util.Map;

public class Point {

    private ReportPanelTitle reportPanelTitle;
    private DriverType driverType;
    private List<ResourceBean> resourceBeans;
    private String grouppingParameter;
    private GrouperPoints grouperPoints;
    private ReportContext driverContext;

    private static LinkedHashMap<String, Point> allPoints;
    private ArrayList<ResourceBean> resourcebeansOfTI = new ArrayList<>();
    private ArrayList<ResourceBean> resourcebeansOfTS = new ArrayList<>();

    public int getCountTS(){
        return getResourcebeansOfTS().size();
    }
    public int getCountTI(){
        return getResourcebeansOfTI().size();
    }
    public int getCountLabels() {
        return getReportPanelTitle().getCountLabels();
    }

    private Point(Builder builder)
            throws ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
        setResourceBeans(builder.resourceBeans);
        setGrouperPoints(builder.grouperPoints);

        addGrouppingParameter();
        addDriverType();
        addReportPanelTitle();
        splitBeansToTSTI();
        setDriverStrategy();


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

    private void setDriverStrategy(){
        //ReportContext reportContext = new ReportContext();
        driverContext = new ReportContext();
        switch (driverType){
            case IEC870:
                driverContext.setReportStrategy(new Iec870Strategy());
                break;
            case IEC850:
                driverContext.setReportStrategy(new Iec850Strategy());
                break;
            case SPRECON850:
                driverContext.setReportStrategy(new Iec850SprStrategy());
                break;
            case SPRECON870:
                driverContext.setReportStrategy(new Iec870SprStrategy());
                break;
            default:
                break;
        }
    }



    public ReportContext getDriverContext(){
        return driverContext;
    }

    public static ArrayList<Point> getAllPoints(){
        if (allPoints == null){
            allPoints = new LinkedHashMap<>();
        }

        return new ArrayList<>(allPoints.values());
    }

    private void splitBeansToTSTI(){
        for(ResourceBean resourceBean : getResourceBeans()) {
            if (resourceBean.isVariableTI()) {
                resourcebeansOfTI.add(resourceBean);
            } else {
                resourcebeansOfTS.add(resourceBean);
            }
        }
    }

    public ArrayList<ResourceBean> getResourcebeansOfTS() {
        return resourcebeansOfTS;
    }

    public ArrayList<ResourceBean> getResourcebeansOfTI() {
        return resourcebeansOfTI;
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

    private void addReportPanelTitle()
            throws ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
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
            allPoints = new LinkedHashMap<>();
        }
        allPoints.clear();
    }

    public static ArrayList<Point> buildPoints(List<ResourceBean> resourceBeans,
                                               GrouperPoints grouperPoints){

        /*DistributerToPoints.buildPoints(resourceBeans, grouperPoints).forEach(resourceBeans1 ->
                {
                    try {
                        new Builder()
                                .resourceBeans(resourceBeans1)
                                .grouperParameter(grouperPoints)
                                .build();
                    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
        );*/

        //DistributerToPoints.buildPoints(resourceBeans, grouperPoints);

        Map<Integer, List<ResourceBean>> points = DistributerToPoints.buildPoints(resourceBeans, grouperPoints);

        points.values().forEach(resourceBeans1 ->
                {
                    try {
                        new Builder()
                                .resourceBeans(resourceBeans1)
                                .grouperParameter(grouperPoints)
                                .build();
                    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
        );

                LogInfo.setLogDataWithTitle(
                "Количество устройств для создания протокола",
                String.valueOf(Point.getPointsCount()));

        return new ArrayList<>(allPoints.values());
    }

    public ReportPanelTitle getReportPanelTitle() {
        return reportPanelTitle;
    }

    private void setReportPanelTitle(ResourceBean resourceBean)
            throws ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
        if (isSpreconTable()) {
            this.reportPanelTitle = new ReportPanelSprTitle(resourceBean, grouperPoints);
        } else {
            this.reportPanelTitle = new ReportPanelTitle(resourceBean, grouperPoints);
        }
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

    public void clearBeansInPoint() {
        resourcebeansOfTI.clear();
        resourcebeansOfTI.clear();
    }

    public static final class Builder {
        private List<ResourceBean> resourceBeans;
        private GrouperPoints grouperPoints;

        public Builder(){}

        public Builder resourceBeans(List<ResourceBean> val) {
            resourceBeans = val;
            return this;
        }
        
        public Builder grouperParameter(GrouperPoints val){
            grouperPoints = val;
            return this;
        }

        public Point build()
                throws ParserConfigurationException, SAXException,
                XPathExpressionException, IOException {
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
