package reportV460.Report.Parsers;

import reportV460.v460.ResourceBean;

import java.io.IOException;
import java.util.List;

public class ValidatorResourceBeans {
    private List<ResourceBean> resourceBeans;
    private List<EnipObject> enipObjects;

    public ValidatorResourceBeans(String txtV460Variables,
                                  List<EnipObject> enips) throws IOException {

        this(new ParserVariablesV460(txtV460Variables).getBeansFromCsv(),
                enips);
    }

    public ValidatorResourceBeans(String txtV460Variables,
                                  String dirEnipsConfigurations) throws IOException {

        this(new ParserVariablesV460(txtV460Variables).getBeansFromCsv(),
                ParserEnipJSON.getListOfEnips(dirEnipsConfigurations));
    }

    public ValidatorResourceBeans(List<ResourceBean> resourceBeans, List<EnipObject> enips){
        this.resourceBeans = resourceBeans;
        this.enipObjects = enips;

        addCorrectedDriverType();
        addCoefficientTransform();
    }

    public List<ResourceBean> getReadyBeans(){
        return resourceBeans;
    }

    private void addCorrectedDriverType(){
        resourceBeans.forEach(ResourceBean::setCorrectDriverTypeAfterInitAllFields);
    }

    private void addCoefficientTransform() {
        for (ResourceBean resourceBean : resourceBeans) {
            if (resourceBean.isVariableTI() && !enipObjects.isEmpty()) {
                resourceBean.setCoefficientTransformWithEnips(enipObjects);
            }else {
                resourceBean.setDefaultCoefficientTransform();
            }
        }
    }
}
