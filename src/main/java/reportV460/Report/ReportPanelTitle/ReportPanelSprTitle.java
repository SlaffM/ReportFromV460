package reportV460.Report.ReportPanelTitle;

import org.xml.sax.SAXException;
import reportV460.Report.Parsers.XmlVariablesParser;
import reportV460.v460.ResourceBean;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ReportPanelSprTitle extends ReportPanelTitle {

    private XmlVariablesParser xmlVariablesParser;

    public ReportPanelSprTitle(ResourceBean resourceBean)
            throws ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
        super(resourceBean);
        xmlVariablesParser = new XmlVariablesParser(resourceBean);
    }

    @Override
    public String getPanelTitle() {
        if (!xmlVariablesParser.getPanelTitle().equals("_"))
            return xmlVariablesParser.getPanelTitle();
        else
            return String.format("%s %s", getResourceBean().getVoltageClass(), getResourceBean().getConnectionTitle());
    }

    @Override
    public String getPanelLocation() {
        if (!xmlVariablesParser.getPanelNumber().equals("_"))
            return xmlVariablesParser.getPanelNumber();
        else
            return super.getPanelLocation();
    }

    @Override
    public String getConnectionTitle() {
        return getResourceBean().getVoltageClass();
    }

    @Override
    public String getControllerTitle() {
        if (!xmlVariablesParser.getControllerTitle().equals("_"))
            return xmlVariablesParser.getControllerTitle();
        else
            return "Контроллер №";
    }

}
