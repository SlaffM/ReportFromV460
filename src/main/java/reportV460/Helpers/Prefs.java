package reportV460.Helpers;

import java.util.prefs.Preferences;

public class Prefs {

    static Preferences prefs;

    static{
        prefs = Preferences.userNodeForPackage(reportV460.Main.class);
        final String prefIP = "IP";
    }

    public static void setPrefValue(String prefName, String prefValue){
        prefs.put(prefName, prefValue);
    }

    public static String getPrefValue(String prefName){
        return prefs.get(prefName, "");
    }




}
