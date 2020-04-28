package reportV460.Report.ReportPanelTitle;

import org.apache.xmlbeans.impl.xb.xsdschema.Group;
import reportV460.Helpers.Prefs;
import reportV460.Report.Parsers.DriverObject;
import reportV460.v460.GrouperPoints;
import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;
import java.util.prefs.Preferences;

public class ReportPanelTitle {

    private ResourceBean resourceBean;
    private GrouperPoints grouperPoints;
    private int countLabels;

    public ReportPanelTitle(ResourceBean resourceBean, GrouperPoints grouperPoints){
        this.resourceBean = resourceBean;
        this.grouperPoints = grouperPoints;
    }

    public String getTagname() { return getResourceBean().getTagname(); }
    public String getPanelLocation(){ return getResourceBean().getPanelLocation(); }
    public String getPanelTitle(){
        return getResourceBean().getConnectionTitle();
    }



    public String getIpAddress() {
        if (DriverObject.getDriversCount() > 0){
            return DriverObject.getDeviceIpFromDrivers(
                    getResourceBean().getDriverName(),
                    getResourceBean().getNetAddr());
        }else {
            return Prefs.getPrefValue("IP") + getResourceBean().getNetAddr();
        }
    }

    public String getConnectionTitle(){
        return getResourceBean().getConnectionTitle();
    }
    public String getControllerTitle(){
        return getResourceBean().getDevice();
    }

    public ResourceBean getResourceBean() {
        return resourceBean;
    }
    public GrouperPoints getGrouperPoints(){return grouperPoints;}

    public LinkedHashMap createHeaders(){
        LinkedHashMap titleTable = new LinkedHashMap<String,String>();

        titleTable.put("Расположение", getPanelLocation());
        titleTable.put("Наименование шкафа", getPanelTitle());
        titleTable.put("Наименование присоединения", getConnectionTitle());
        titleTable.put("Обозначение контроллера", getControllerTitle());
        titleTable.put("IP-адрес", getIpAddress());

        return titleTable;
    }

    public int getCountLabels() {
        return createHeaders().size();
    }
}
