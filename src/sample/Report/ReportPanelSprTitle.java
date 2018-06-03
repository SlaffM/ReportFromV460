package sample.Report;

import sample.v460.ResourceBean;

public class ReportPanelSprTitle extends ReportPanelTitle{

    private String tagname;
    private ResourceBean resourceBean;

    public ReportPanelSprTitle(ResourceBean resourceBean){
        super(resourceBean.getTagname());
        this.resourceBean = resourceBean;
    }

    @Override
    public String getPanelLocation() {
        return getResourceBean().getPanelLocation();
    }

    @Override
    public String getPanelTitle() {
        return String.format("%s %s", getResourceBean().getVoltageClass(), getResourceBean().getConnectionTitle());
    }

    @Override
    public String getConnectionTitle() {
        return getResourceBean().getConnectionTitle();
    }

    @Override
    public String getControllerTitle() {
        return getResourceBean().getPrefixSpreconSymbAddress();
    }

    @Override
    public String getTagname() {
        return tagname;
    }

    @Override
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Override
    public String getIpAddress(){
        return getResourceBean().getIpAddress();
    }

    public ResourceBean getResourceBean() {
        return resourceBean;
    }

}
