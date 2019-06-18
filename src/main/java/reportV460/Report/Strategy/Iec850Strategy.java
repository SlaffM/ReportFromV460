package reportV460.Report.Strategy;

import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public class Iec850Strategy extends IecStrategy {

/*    @Override
    public String[] createHeadersVariablesTS(){
        return new String[]{
                "№ панели",
                "Система",
                "Класс напряж.",
                "Присоединение",
                "Устройство",
                "Наименование сигнала",
                "Текс состояния",
                "Класс тревог",
                "Адрес внутр.",
                "Адрес МЭК 61850"
        };
    }

    @Override
    public String[] createHeadersVariablesTI(){
        return new String[]{
                "№ панели",
                "Система",
                "Класс напряж.",
                "Присоединение",
                "Устройство",
                "Наименование сигнала",
                "Ед. измерения",
                "Ктт, Ктн",
                "Адрес внутр.",
                "Адрес МЭК 61850"
        };
    }*/

    public LinkedHashMap<String, String> createDataTemplateTI(ResourceBean resourceBean){

        LinkedHashMap titleTableTI = new LinkedHashMap<String,String>();
        titleTableTI.put("№ панели", resourceBean.getPanelLocation());
        titleTableTI.put("Система", resourceBean.getSystem());
        titleTableTI.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTI.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTI.put("Устройство", resourceBean.getDevice());
        titleTableTI.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTI.put("Ед. измерения", resourceBean.getUnit());
        titleTableTI.put("Ктт, Ктн", resourceBean.getCoefficientTransform());
        titleTableTI.put("Адрес внутр.", resourceBean.getRecourcesLabel());
        titleTableTI.put("Адрес МЕК 61850", resourceBean.getShortSymbAddress());
        return titleTableTI;
    }


    public LinkedHashMap<String, String> createDataTemplateTS(ResourceBean resourceBean){

        LinkedHashMap titleTableTS = new LinkedHashMap<String,String>();
        titleTableTS.put("№ панели", resourceBean.getPanelLocation());
        titleTableTS.put("Система", resourceBean.getSystem());
        titleTableTS.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTS.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTS.put("Устройство", resourceBean.getDevice());
        titleTableTS.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTS.put("Текс состояния", resourceBean.getStatusText());
        titleTableTS.put("Класс тревог", resourceBean.getAlarmClass());
        titleTableTS.put("Адрес внутр.", resourceBean.getRecourcesLabel());
        titleTableTS.put("Адрес МЕК 61850", resourceBean.getShortSymbAddress());
        return titleTableTS;
    }
}
