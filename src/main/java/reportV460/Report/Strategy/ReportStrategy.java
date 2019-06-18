package reportV460.Report.Strategy;

import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public interface ReportStrategy {
        //void createDocTable(XWPFDocument document, Point point);
        //void createXlsTable(HSSFWorkbook document, Point point);

        /*String[] createHeadersVariablesTS();*/
        /*String[] createHeadersVariablesTI();*/

        /*ArrayList<String> getPropertiesResourceBeanTS(ResourceBean resourceBean);
        ArrayList<String> getPropertiesResourceBeanTI(ResourceBean resourceBean);*/


        void createDataTemplateTS(ResourceBean resourceBean);
        void createDataTemplateTI(ResourceBean resourceBean);

        LinkedHashMap getTitleTableTS();
        LinkedHashMap getTitleTableTI();



}
