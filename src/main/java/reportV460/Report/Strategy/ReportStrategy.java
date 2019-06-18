package reportV460.Report.Strategy;

import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public interface ReportStrategy {
        LinkedHashMap<String, String> createData(ResourceBean resourceBean);
}
