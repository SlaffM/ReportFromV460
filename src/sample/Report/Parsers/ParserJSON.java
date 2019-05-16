package sample.Report.Parsers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParserJSON {

    private static EnipObject getEnipFromJSON(File file){

        JSONParser jsonParser = new JSONParser();
        EnipObject enipObject = new EnipObject();

        try (FileReader reader = new FileReader(file.getAbsolutePath()))
        {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject data = (JSONObject)jsonObject.get("Data");
            JSONObject enip = (JSONObject)data.get("Enip2M");
            String voltageCoef = enip.get("VoltageCoefficient").toString();
            String curCoef = enip.get("CurrentCoefficient").toString();
            String powerCoef = enip.get("PowerCoefficient").toString();
            String ipAddress = enip.get("IPAddress").toString();

            JSONObject iec850 = (JSONObject)enip.get("Iec61850");
            String iedName = iec850.get("IedName").toString();

            enipObject.setVoltageCoefficient(voltageCoef);
            enipObject.setCurrentCoefficient(curCoef);
            enipObject.setPowerCoefficient(powerCoef);
            enipObject.setIPAddress(ipAddress);
            enipObject.setIedName(iedName);

            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return enipObject;
    }

    public static ArrayList<EnipObject> getListOfEnips(File folder){

        ArrayList<EnipObject> enipObjects = new ArrayList<>();
        File[] files = folder.listFiles();

        for (File file : files) {
            enipObjects.add(getEnipFromJSON(file));
        }
        return enipObjects;
    }


}
