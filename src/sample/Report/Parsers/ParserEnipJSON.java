package sample.Report.Parsers;
import org.apache.commons.logging.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Helpers.LogInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParserEnipJSON {

    private static EnipObject getEnipFromJSON(File file){

        JSONParser jsonParser = new JSONParser();


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

            EnipObject enipObject = new EnipObject(
                    voltageCoef,
                    curCoef,
                    powerCoef,
                    ipAddress,
                    iedName
            );

            return enipObject;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new EnipObject();
    }

    public static ArrayList<EnipObject> getListOfEnips(File folder){
        if (folder.isDirectory() && folder.exists()){
            File[] files = folder.listFiles();
            for (File file : files) {
                new EnipObject(getEnipFromJSON(file));
            }
            LogInfo.setLogDataWithTitle("Прочитаны конфигурации ЭНИПов",
                    String.valueOf(EnipObject.getEnipsCount()));
            return EnipObject.getAllEnips();
        }
        LogInfo.setErrorData("Директория с конфигурациями ENIP отсутствует!");
        return new ArrayList<>();
    }
}
