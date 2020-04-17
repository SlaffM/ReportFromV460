package reportV460.Report.Strategy;

import reportV460.Helpers.Prefs;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public class Iec870SprStrategy extends IecStrategy {

    public LinkedHashMap<String,String> createDataHeaders(ResourceBean resourceBean){

        LinkedHashMap<String, String> titleTable = new LinkedHashMap();
        titleTable.put("№ панели", resourceBean.getPanelLocation());
        titleTable.put("Система", resourceBean.getSystem());
        titleTable.put("Класс напряж.", resourceBean.getVoltageClass());
        titleTable.put("Присоединение", resourceBean.getConnectionTitle());
        titleTable.put("Устройство", resourceBean.getDevice());
        titleTable.put("Наименование сигнала", resourceBean.getSignalName());
        if(resourceBean.isVariableTI()) {
            titleTable.put("Ед. измерения", resourceBean.getUnit());
            titleTable.put("Ктт, Ктн", resourceBean.getCoefficientTransform());
        }else{
            titleTable.put("Текст. состояние", resourceBean.getStatusText());
            titleTable.put("Класс тревог", resourceBean.getAlarmClass());
        }
        titleTable.put("Адрес Sprecon", resourceBean.getRecourcesLabel());
        titleTable.put("Тип АСДУ", resourceBean.getIec870_type());
        titleTable.put("Адрес АСДУ", resourceBean.getIec870_coa1());
        titleTable.put("Адрес объекта", resourceBean.getIec870_ioa1());
        titleTable.put("Результат", Prefs.getPrefValue("RESULT"));
        titleTable.put("Примечание", resourceBean.getSignRV());

        return titleTable;

    }


}
