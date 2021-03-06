package reportV460.Report.Strategy;

import reportV460.v460.ResourceBean;
import java.util.ArrayList;

public class Iec870Strategy extends IecStrategy {

    @Override
    String[] createHeadersVariables(){
        return new String[]{
                "№ панели",
                "Система",
                "Класс напряж.",
                "Присоединение",
                "Устройство",
                "Наименование сигнала",
                "Текс состояния",
                "Класс тревог",
                "Тип АСДУ",
                "Адрес АСДУ",
                "Адрес объекта"
        };
    }

    @Override
    String[] createHeadersVariablesTI(){
        return new String[]{
                "№ панели",
                "Система",
                "Класс напряж.",
                "Присоединение",
                "Устройство",
                "Наименование сигнала",
                "Ед. измерения",
                "Ктт, Ктн",
                "Тип АСДУ",
                "Адрес АСДУ",
                "Адрес объекта"
        };
    }

    @Override
    ArrayList<String> getPropertiesResourceBean(ResourceBean resourceBean){
        ArrayList<String> props = new ArrayList<>();

        props.add(resourceBean.getPanelLocation());
        props.add(resourceBean.getSystem());
        props.add(resourceBean.getVoltageClass());
        props.add(resourceBean.getConnectionTitle());
        props.add(resourceBean.getDevice());
        props.add(resourceBean.getSignalName());
        props.add(resourceBean.getStatusText());
        props.add(resourceBean.getAlarmClass());
        props.add(resourceBean.getIec870_type());
        props.add(resourceBean.getIec870_coa1());
        props.add(resourceBean.getIec870_ioa1());

        return props;
    }

    @Override
    ArrayList<String> getPropertiesResourceBeanTI(ResourceBean resourceBean){
        ArrayList<String> props = new ArrayList<>();

        props.add(resourceBean.getPanelLocation());
        props.add(resourceBean.getSystem());
        props.add(resourceBean.getVoltageClass());
        props.add(resourceBean.getConnectionTitle());
        props.add(resourceBean.getDevice());
        props.add(resourceBean.getSignalName());
        props.add(resourceBean.getUnit());
        props.add(resourceBean.getCoefficientTransform());
        props.add(resourceBean.getIec870_type());
        props.add(resourceBean.getIec870_coa1());
        props.add(resourceBean.getIec870_ioa1());

        return props;
    }

}
