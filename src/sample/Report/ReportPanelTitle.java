package sample.Report;

import sample.v460.ResourceBean;

public class ReportPanelTitle {

    private String tagname;
    private String idAddress;
    private ResourceBean resourceBean;

    public ReportPanelTitle(String tagname) {
        this.tagname = tagname;
    }

    public ReportPanelTitle(ResourceBean resourceBean){
        this.resourceBean = resourceBean;
    }

    public String getTagname() {
        return tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getPanelLocation(){
        return getResourceBean().getPanelLocation();
    }
    public String getPanelTitle(){
        return getResourceBean().getDevice();
    }

    public String getIpAddress() {
        return getResourceBean().getIpAddress();
    }

    public ResourceBean getResourceBean() {
        return resourceBean;
    }

    public String getConnectionTitle(){
        return getResourceBean().getConnectionTitle();
    }
    public String getControllerTitle(){
        return getResourceBean().getConnectionTitle();
    }
}
