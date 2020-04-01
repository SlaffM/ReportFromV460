package reportV460.Report.ReportPanelTitle;

import org.xml.sax.SAXException;
import reportV460.Report.Parsers.XmlVariablesParser;
import reportV460.v460.GrouperPoints;
import reportV460.v460.ResourceBean;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ReportPanelSprTitle extends ReportPanelTitle {

    private XmlVariablesParser xmlVariablesParser;

    public ReportPanelSprTitle(ResourceBean resourceBean, GrouperPoints grouperPoints)
            throws ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
        super(resourceBean, grouperPoints);
        xmlVariablesParser = new XmlVariablesParser(resourceBean);
    }

    @Override
    public String getPanelTitle() {
        switch (getGrouperPoints()) {
            case GROUP_BY_NETADDR:
                if (!xmlVariablesParser.getPanelTitle().equals("_"))
                    return xmlVariablesParser.getPanelTitle();
                else
                    return String.format("%s %s", getResourceBean().getVoltageClass(), getResourceBean().getConnectionTitle());
            case GROUP_BY_PANEL:
                return getResourceBean().getDevice();
            default:
                return getResourceBean().getDevice();
        }
    }


    @Override
    public String getPanelLocation() {
        switch (getGrouperPoints()){
            case GROUP_BY_NETADDR:
                if (!xmlVariablesParser.getPanelNumber().equals("_"))
                    return xmlVariablesParser.getPanelNumber();
                else
                    return super.getPanelLocation();
            default:
                return getResourceBean().getPanelLocation();
        }
    }

    @Override
    public String getConnectionTitle() {
        //return getResourceBean().getVoltageClass();
        switch (getGrouperPoints()){
            case GROUP_BY_NETADDR:
                return "";
            case GROUP_BY_PANEL:
                return getResourceBean().getConnectionTitle();
        }
        return "";
    }

    @Override
    public String getControllerTitle() {
        if (!xmlVariablesParser.getControllerTitle().equals("_"))
            return xmlVariablesParser.getControllerTitle();
        else
            return "Контроллер №";
    }

}
