package sample.Report;

public class ReportPanelTitle {

    private String tagname;

    public ReportPanelTitle(String tagname) {
        this.tagname = tagname;
    }

    public String getTagname() {
        return tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getPanelLocation(){
        return getTagname().substring(0,8).trim();
    }
    public String getPanelTitle(){
        return getTagname().substring(51).trim();
    }
    public String getConnectionTitle(){
        return getTagname().substring(24,51).trim();
    }
    public String getControllerTitle(){
        return getTagname().substring(51).trim();
    }
}
