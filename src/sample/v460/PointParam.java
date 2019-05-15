package sample.v460;

import sample.Report.ReportPanelTitle.ReportPanelSprTitle;
import sample.Report.ReportPanelTitle.ReportPanelTitle;

import java.util.ArrayList;
import java.util.Collections;

public class PointParam {

    private ReportPanelTitle reportPanelTitle;
    private DriverType driverType;
    private ArrayList<ResourceBean> resourceBeans;

    private PointParam(Builder builder) {
        setDriverType(builder.driverType);
        setReportPanelTitle(builder.resourceBean);
        setResourceBeans(builder.resourceBeans);
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

    private boolean isSpreconTable(){
        return getDriverType().equals(DriverType.SPRECON850) || getDriverType().equals(DriverType.SPRECON870);
    }

    public static final class Builder {
        private ResourceBean resourceBean;
        private DriverType driverType;
        private ArrayList<ResourceBean> resourceBeans;

        public Builder() {
        }

        public Builder driverType(DriverType val) {
            driverType = val;
            return this;
        }

        public Builder reportPanelTitle(ResourceBean val) {
            resourceBean = val;
            return this;
        }

        public Builder resourceBeans(ArrayList<ResourceBean> val) {
            resourceBeans = val;
            return this;
        }

        public PointParam build() {
            return new PointParam(this);
        }
    }
}
