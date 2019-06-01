package sample.Report.Parsers;


import java.util.*;

public class EnipObject {


    private String VoltageCoefficient;
    private String CurrentCoefficient;
    private String PowerCoefficient;
    private String IPAddress;
    private String IedName;
    private int id;
    private static Map<Integer, EnipObject> allEnips;
    private static int countId = 0;


    public EnipObject(){
        new EnipObject("","","","","");
    }

    public EnipObject(EnipObject enip){
        new EnipObject(
                enip.getVoltageCoefficient(),
                enip.getCurrentCoefficient(),
                enip.getPowerCoefficient(),
                enip.getIPAddress(),
                enip.getIedName()
        );
    }

    public EnipObject(
            String voltageCoefficient,
            String currentCoefficient,
            String powerCoefficient,
            String iPAddress,
            String iedName) {

        VoltageCoefficient = voltageCoefficient;
        CurrentCoefficient = currentCoefficient;
        PowerCoefficient = powerCoefficient;
        IPAddress = iPAddress;
        IedName = iedName;

        if (!hasEnip()){
            countId++;
            this.id = countId;
            allEnips.put(id, this);
        }
    }

    private boolean hasEnip(){
        for(EnipObject enip: getAllEnips()){
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
        EnipObject that = (EnipObject) o;
        return  getNetAddress().equals(that.getNetAddress());

    }

    @Override
    public int hashCode() {
        return Objects.hash(IPAddress, IedName);
    }

    public static ArrayList<EnipObject> getAllEnips(){
        if (allEnips == null){
            allEnips = new HashMap<>();
        }
        return new ArrayList<>(allEnips.values());
    }

    public static int getEnipsCount(){
        return getAllEnips().size();
    }


    public String getVoltageCoefficient() {
        return VoltageCoefficient;
    }

    public void setVoltageCoefficient(String voltageCoefficient) {
        VoltageCoefficient = voltageCoefficient;
    }

    public String getCurrentCoefficient() {
        return CurrentCoefficient;
    }

    public void setCurrentCoefficient(String currentCoefficient) {
        CurrentCoefficient = currentCoefficient;
    }

    public String getPowerCoefficient() {
        return PowerCoefficient;
    }

    public void setPowerCoefficient(String powerCoefficient) {
        PowerCoefficient = powerCoefficient;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getIedName() {
        return IedName;
    }

    public void setIedName(String iedName) {
        IedName = iedName;
    }

    public String getNetAddress() {
        if ((getIPAddress() == null) || (getIPAddress().isEmpty())){ return ""; }
        return getIPAddress().split("\\.")[3];
    }

    public static void clearAllEnips(){
        if (allEnips == null){
            allEnips = new HashMap<>();
        }
        allEnips.clear();
    }

    @Override
    public String toString() {
        return "EnipObject{" +
                "id=" + id +
                ", VoltageCoefficient='" + VoltageCoefficient + '\'' +
                ", CurrentCoefficient='" + CurrentCoefficient + '\'' +
                ", PowerCoefficient='" + PowerCoefficient + '\'' +
                ", IPAddress='" + IPAddress + '\'' +
                ", NetAddress='" + getNetAddress() + '\'' +
                ", IedName='" + IedName + '\'' +
                '}';
    }
}
