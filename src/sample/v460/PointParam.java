package sample.v460;

import sample.Report.ReportPanelTitle;
import sample.Report.ReportStrategy;

import java.util.ArrayList;

public class PointParam {

    private ReportPanelTitle reportPanelTitle;
    private DriverType driverType;
    private ArrayList<ResourceBean> resourceBeans;

    public ReportPanelTitle getReportPanelTitle() {
        return reportPanelTitle;
    }

    public void setReportPanelTitle(ReportPanelTitle reportPanelTitle) {
        this.reportPanelTitle = reportPanelTitle;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public ArrayList<ResourceBean> getResourceBeans() {
        return resourceBeans;
    }

    public void setResourceBeans(ArrayList<ResourceBean> resourceBeans) {
        this.resourceBeans = resourceBeans;
    }
}
