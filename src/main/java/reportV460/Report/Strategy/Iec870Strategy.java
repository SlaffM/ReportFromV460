package reportV460.Report.Strategy;

import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public class Iec870Strategy extends IecStrategy {



    /*@Override
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
                "Тип АСДУ",
                "Адрес АСДУ",
                "Адрес объекта"
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
                "Тип АСДУ",
                "Адрес АСДУ",
                "Адрес объекта"
        };
    }*/
/*
    @Override
    public ArrayList<String> getPropertiesResourceBeanTS(ResourceBean resourceBean){
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
    public ArrayList<String> getPropertiesResourceBeanTI(ResourceBean resourceBean){
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
    }*/


    public void createDataTemplateTI(ResourceBean resourceBean){

        LinkedHashMap titleTableTI = new LinkedHashMap<String,String>();
        titleTableTI.put("№ панели", resourceBean.getPanelLocation());
        titleTableTI.put("Система", resourceBean.getSystem());
        titleTableTI.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTI.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTI.put("Устройство", resourceBean.getDevice());
        titleTableTI.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTI.put("Ед. измерения", resourceBean.getUnit());
        titleTableTI.put("Ктт, Ктн", resourceBean.getCoefficientTransform());
        titleTableTI.put("Тип АСДУ", resourceBean.getIec870_type());
        titleTableTI.put("Адрес АСДУ", resourceBean.getIec870_coa1());
        titleTableTI.put("Адрес объекта", resourceBean.getIec870_ioa1());
    }


    public void createDataTemplateTS(ResourceBean resourceBean){

        LinkedHashMap titleTableTS = new LinkedHashMap<String,String>();
        titleTableTS.put("№ панели", resourceBean.getPanelLocation());
        titleTableTS.put("Система", resourceBean.getSystem());
        titleTableTS.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTableTS.put("Присоединение", resourceBean.getConnectionTitle());
        titleTableTS.put("Устройство", resourceBean.getDevice());
        titleTableTS.put("Наименование сигнала", resourceBean.getSignalName());
        titleTableTS.put("Текс состояния", resourceBean.getStatusText());
        titleTableTS.put("Класс тревог", resourceBean.getAlarmClass());
        titleTableTS.put("Тип АСДУ", resourceBean.getIec870_type());
        titleTableTS.put("Адрес АСДУ", resourceBean.getIec870_coa1());
        titleTableTS.put("Адрес объект", resourceBean.getIec870_ioa1());

    }



}
