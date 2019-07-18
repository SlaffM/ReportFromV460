package reportV460.Report.ReportPanelTitle;

import reportV460.Helpers.Prefs;
import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;
import java.util.prefs.Preferences;

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
        return Prefs.getPrefValue("IP") + getResourceBean().getNetAddr();
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

    public LinkedHashMap createHeaders(){
        LinkedHashMap titleTable = new LinkedHashMap<String,String>();

        titleTable.put("Расположение", getPanelLocation());
        titleTable.put("Наименование шкафа", getPanelTitle());
        titleTable.put("Наименование присоединения", getConnectionTitle());
        titleTable.put("Обозначение контроллера", getControllerTitle());
        titleTable.put("IP-адрес", getIpAddress());

        return titleTable;
    }
}
