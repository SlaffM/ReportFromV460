package sample.Report.Strategy;

public class Iec850SpreconReportStrategy extends IecReportStrategy {

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
                "Адрес Sprecon",
                "Адрес МЕК 61850"
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
                "Адрес Sprecon",
                "Адрес МЕК 61850"
        };
    }


}
