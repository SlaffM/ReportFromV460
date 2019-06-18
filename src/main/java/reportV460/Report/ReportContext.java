package reportV460.Report;

import reportV460.Report.Strategy.ReportStrategy;
import reportV460.v460.ResourceBean;

public class ReportContext {

    ReportStrategy reportStrategy;

    public ReportStrategy getReportStrategy() {
        return reportStrategy;
    }

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

}
