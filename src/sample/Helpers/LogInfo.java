package sample.Helpers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import javax.naming.Binding;
import java.util.List;

public final class LogInfo {

    private static SimpleListProperty logData = new SimpleListProperty<>(FXCollections.observableArrayList());

    // methods that set/format logData based on changes from your UI

    // provide public access to the property
    public static SimpleListProperty logDataProperty() {
        return logData;
    }


    public static void setLogData(String data) {
        //if (getLogData() == null) { logData.add(new SimpleStringProperty(data));}
        //else {logData.add(new SimpleStringProperty(data));}
        logData.add(new SimpleStringProperty(data).getValue());

        /*
        if (getLogData() == null) { logData.set(data);}
        else {logData.set(getLogData() + "\n" + data);}*/

    }

    private static List getLogData() {
        return logData.get();
    }

    public static void setLogDataWithTitle(String title, String data){
        setLogData(title + ":\t" + data);
    }

    public static void setErrorData(String data){
        setLogDataWithTitle("Ошибка:", data);
    }

}
