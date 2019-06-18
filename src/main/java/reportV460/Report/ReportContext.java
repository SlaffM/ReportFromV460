package reportV460.Report;

import reportV460.Report.Strategy.ReportStrategy;
import reportV460.v460.ResourceBean;

public class ReportContext {

    ReportStrategy reportStrategy;
    ResourceBean resourceBeanTS;
    ResourceBean resourceBeanTI;

    public ReportStrategy getReportStrategy() {
        return reportStrategy;
    }

    public void setReportStrategy(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
        if (!(getResourceBeanTI() == null)) { this.reportStrategy.createDataTemplateTI(getResourceBeanTI());}
        if (!(getResourceBeanTS() == null)) { this.reportStrategy.createDataTemplateTS(getResourceBeanTS());}
        //this.reportStrategy.createDataTemplateTI(getResourceBeanTI());
        //this.reportStrategy.createDataTemplateTS(getResourceBeanTS());
    }

    public ResourceBean getResourceBeanTS() {
        return resourceBeanTS;
    }

    public void setResourceBeanTS(ResourceBean resourceBean) {
        this.resourceBeanTS = resourceBean;
    }

    public void setResourceBeanTI(ResourceBean resourceBean) {
        this.resourceBeanTI = resourceBean;
    }

    public ResourceBean getResourceBeanTI() {
        return resourceBeanTI;
    }

    /*public void createDocTable(XWPFDocument document, Point point){
        reportStrategy.createDocTable(document, point);
    }

    public void createXlsTable(HSSFWorkbook document, Point point){
        reportStrategy.createXlsTable(document, point);
    }*/



}
