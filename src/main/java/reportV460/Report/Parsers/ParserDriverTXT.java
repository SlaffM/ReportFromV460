package reportV460.Report.Parsers;
import reportV460.Helpers.LogInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParserDriverTXT {

    private static void createDevicesForDriver(File file){

        try {
            Path path = Paths.get(file.getPath());

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            String driverName = file.getName().replace(".txt", "");

            for(int l = 0; l < lines.size()-1; l++){
                String line = lines.get(l);

                if (driverName.startsWith("IEC850")) {
                    if (line.equals("*** SERVER ***")) {
                        String netAddr = lines.get(l + 2);
                        String ipAddress = lines.get(l + 4);
                        new DriverObject(driverName, ipAddress, netAddr);
                    }
                }else if(driverName.startsWith("IEC870")){
                    if (line.equals("*** LINK ***")) {
                        String netAddr = lines.get(l + 2);
                        String ipAddress = lines.get(l + 5);
                        new DriverObject(driverName, ipAddress, netAddr);
                    }
                }else{}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<DriverObject> createDrivers(String dir){
        File folder = new File(dir);
        if (folder.isDirectory() && folder.exists()){
            File[] files = folder.listFiles();
            for (File file : files) {
                if(file.getName().endsWith(".txt")) createDevicesForDriver(file);
            }
            LogInfo.setLogDataWithTitle("Прочитаны конфигурации ЭНИПов",
                    String.valueOf(DriverObject.getAllDrivers().size()));
            return DriverObject.getAllDrivers();
        }
        LogInfo.setErrorData("Директория с конфигурациями ENIP отсутствует!");
        return new ArrayList<>();
    }
}
