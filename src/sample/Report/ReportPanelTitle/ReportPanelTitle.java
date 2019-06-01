package sample.Report.ReportPanelTitle;

import sample.v460.ResourceBean;

public class ReportPanelTitle {

    private ResourceBean resourceBean;

    public ReportPanelTitle(ResourceBean resourceBean){
        this.resourceBean = resourceBean;
    }

    public String getTagname() { return getResourceBean().getTagname(); }
    public String getPanelLocation(){ return getResourceBean().getPanelLocation(); }
    public String getPanelTitle(){
        return getResourceBean().getDevice();
    }
    public String getIpAddress() {
        return getResourceBean().getIpAddress();
    }
    public String getConnectionTitle(){
        return getResourceBean().getConnectionTitle();
    }
    public String getControllerTitle(){
        return getResourceBean().getConnectionTitle();
    }

    public ResourceBean getResourceBean() {
        return resourceBean;
    }
}
