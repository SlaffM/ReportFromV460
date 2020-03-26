package reportV460.Report.Parsers;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import reportV460.Helpers.Helpers;
import reportV460.Helpers.LogInfo;
import reportV460.Helpers.Prefs;
import reportV460.v460.ResourceBean;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

public class XmlVariablesParser {

    private String panelNumber;
    private String panelTitle;
    private String controllerTitle;

    public XmlVariablesParser(ResourceBean resourceBean)
            throws ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
        String sprName = Helpers.getTextWithPattern(resourceBean.getRecourcesLabel(), "(\\w{2}\\.\\w{2})");


        if (isFecOsnController(sprName)){
            sprName = "SPR.STC01.Connect_vba";
        }else if(isFecResController(sprName)){
            sprName = "SPR.STC81.Connect_vba";
        }
        else {
            sprName =   "SPR." +
                        sprName.replace(".", "").replace("F2", "00") +
                        ".Connect_vba";
        }
        String findedTagname = getDataOnRequest(sprName);
        setPanelNumber(Helpers.getPanelLocation(findedTagname));
        setPanelTitle(Helpers.getConnectionTitle(findedTagname));
        setControllerTitle(Helpers.getDevice(findedTagname));
    }

    private boolean isFecOsnController(String sprName){
        return sprName.startsWith("01");
    }

    private boolean isFecResController(String sprName){
        return sprName.startsWith("81");
    }

    public String getPanelNumber() {
        return panelNumber;
    }

    public void setPanelNumber(String panelNumber) {
        this.panelNumber = panelNumber;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public String getControllerTitle() {
        return controllerTitle;
    }

    public void setControllerTitle(String controllerTitle) {
        this.controllerTitle = controllerTitle;
    }

    private String getDataOnRequest(String sprName)
            throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        File file = new File(Prefs.getPrefValue("PathProgramm"));
        if (file.isDirectory() || !file.exists()) return "";

        Document doc = builder.parse(new File(Prefs.getPrefValue("PathProgramm")));

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr
                = xpath.compile("//Apartment/Variable[Name = " + "'" + sprName + "'" + "]/Tagname/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        LogInfo.setLogData("Найдена переменная - " + sprName);

        if(nodes.getLength() == 0) return "";
        else                       return nodes.item(0).getNodeValue();
    }

}
