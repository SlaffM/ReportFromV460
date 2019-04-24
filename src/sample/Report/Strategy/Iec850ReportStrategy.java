package sample.Report.Strategy;

public class Iec850ReportStrategy extends IecReportStrategy {

    @Override
    String[] createHeadersVariables(){
        String[] variablesTableHeaders = new String[]{
                "№ панели",
                "Система",
                "Класс напряж.",
                "Присоединение",
                "Устройство",
                "Наименование сигнала",
                "Текс состояния",
                "Класс тревог",
                "Адрес Экра",
                "Адрес Экра IEC850"
        };
        return variablesTableHeaders;
    }
}
