package sample.Report.ReportPanelTitle;

import sample.v460.ResourceBean;

public class ReportPanelSprTitle extends ReportPanelTitle {

    public ReportPanelSprTitle(ResourceBean resourceBean){ super(resourceBean); }

    @Override
    public String getPanelTitle() {
        return String.format("%s %s", getResourceBean().getVoltageClass(), getResourceBean().getConnectionTitle());
    }

    @Override
    public String getControllerTitle() {
        return getResourceBean().getPrefixSpreconSymbAddress();
    }

}
