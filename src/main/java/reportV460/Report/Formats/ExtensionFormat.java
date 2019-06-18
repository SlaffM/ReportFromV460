package reportV460.Report.Formats;

import reportV460.Report.ReportPanelTitle.ReportPanelTitle;
import reportV460.Report.Strategy.ReportStrategy;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.util.ArrayList;
import java.util.Map;

public interface ExtensionFormat {
    void writeDocument();
    void initPropertiesDocument();
    void createTables(Point point);

    void createTitlePanel(ReportPanelTitle reportPanelTitle);
    void createVariablesPanel(Point point);

    void addHeadersToVariablesPanel(Map<String, String> headers);
    void addVariablesToVariablesPanel(ReportStrategy reportStrategy, ArrayList<ResourceBean> resourceBeans);
}
