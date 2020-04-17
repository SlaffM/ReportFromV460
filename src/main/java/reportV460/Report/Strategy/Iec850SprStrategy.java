package reportV460.Report.Strategy;

import reportV460.Helpers.Prefs;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.util.LinkedHashMap;

public class Iec850SprStrategy extends IecStrategy {

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
        titleTable.put("Адрес МЭК-61850", resourceBean.getShortSymbAddress());
        titleTable.put("Результат", Prefs.getPrefValue("RESULT"));
        titleTable.put("Примечание", resourceBean.getSignRV());

        return titleTable;

    }


}
