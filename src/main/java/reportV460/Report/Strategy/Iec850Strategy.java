package reportV460.Report.Strategy;

import reportV460.v460.ResourceBean;

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

    public void createDataTemplateTI(ResourceBean resourceBean){

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
    }


    public void createDataTemplateTS(ResourceBean resourceBean){

        titleTableTS.put("№ панели", resourceBean.getPanelLocation());
        titleTableTS.put("Система", resourceBean.getSystem());
        titleTableTS.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTS.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTS.put("Устройство", resourceBean.getDevice());
        titleTableTS.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTS.put("Текс состояния", resourceBean.getStatusText());
        titleTableTS.put("Класс тревог", resourceBean.getAlarmClass());
        titleTableTS.put("Адрес Sprecon", resourceBean.getRecourcesLabel());
        titleTableTS.put("Адрес МЕК 61850", resourceBean.getShortSymbAddress());

    }
}
