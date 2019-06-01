package sample.Helpers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class LogInfo {

    private static StringProperty logData = new SimpleStringProperty();

    // methods that set/format logData based on changes from your UI

    // provide public access to the property
    public static StringProperty logDataProperty() {
        return logData;
    }

    public static void setLogData(String data) {
        if (getLogData() == null) { logData.set(data);}
        else {logData.set(getLogData() + "\n" + data);}
    }

    private static String getLogData() {
        return logData.get();
    }

    public static void setLogDataWithTitle(String title, String data){
        setLogData(title + ":\n\t" + data);
    }

    public static void setErrorData(String data){
        setLogDataWithTitle("Ошибка:", data);
    }

}
