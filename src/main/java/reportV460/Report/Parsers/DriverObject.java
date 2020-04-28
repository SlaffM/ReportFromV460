package reportV460.Report.Parsers;


import org.apache.commons.lang3.ObjectUtils;
import reportV460.Helpers.LogInfo;
import reportV460.Helpers.Prefs;

import java.util.ArrayList;
import java.util.Objects;

public class DriverObject {

    private String DriverName;
    private String IPAddress;
    private String NetAddr;
    private static ArrayList<DriverObject> allDrivers;


    public DriverObject(){
        new DriverObject("","","");
    }

    public DriverObject(DriverObject driverObject){
        new DriverObject(
                driverObject.getDriverName(),
                driverObject.getIPAddress(),
                driverObject.getNetAddr()
        );
    }

    public DriverObject(
            String driverName,
            String iPAddress,
            String netAddr) {

        DriverName = driverName;
        IPAddress = iPAddress;
        NetAddr = netAddr;

        if (!hasDevice()) {
            allDrivers.add(this);
        }
    }

    private boolean hasDevice(){
        for(DriverObject enip: getAllDrivers()){
            if(enip.equals(this) && enip.hashCode() == this.hashCode()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverObject that = (DriverObject) o;
        return  getDriverName().equals(that.getDriverName()) &&
                getNetAddr().equals(that.getNetAddr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(DriverName, NetAddr);
    }

    public static ArrayList<DriverObject> getAllDrivers(){
        if (allDrivers == null){
            allDrivers = new ArrayList<>();
        }
        return allDrivers;
    }

    public static String getDeviceIpFromDrivers(String driverName, String netAddr){
        DriverObject drObject = allDrivers.stream()
                .filter(driverObject -> driverObject.getDriverName().contains(driverName))
                .filter(driverObject -> netAddr.equals(driverObject.getNetAddr()))
                .findFirst()
                .orElse(null);
        if (drObject == null){
            LogInfo.setErrorData("Не найден IP для устройства: " + driverName + ", " + netAddr);
            return Prefs.getPrefValue("IP");
        }else{
            return drObject.getIPAddress();
        }
    }


    public static int getDriversCount(){
        return getAllDrivers().size();
    }

    public String getDriverName() {
        return DriverName;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getNetAddr() {
        return NetAddr;
    }

    public static void clearAllDrivers(){
        if (allDrivers == null){
            allDrivers = new ArrayList<>();
        }
        allDrivers.clear();
    }

    @Override
    public String toString() {
        return "EnipObject{" +
                ", DriverName='" + DriverName + '\'' +
                ", IPAddress='" + IPAddress + '\'' +
                ", NetAddr='" + NetAddr + '\'' +
                '}';
    }
}
