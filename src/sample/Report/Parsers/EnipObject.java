package sample.Report.Parsers;

public class EnipObject {

    private String VoltageCoefficient;
    private String CurrentCoefficient;
    private String PowerCoefficient;
    private String IPAddress;
    private String IedName;
    private String NetAddress;

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

    @Override
    public String toString() {
        return "EnipObject{" +
                "VoltageCoefficient='" + VoltageCoefficient + '\'' +
                ", CurrentCoefficient='" + CurrentCoefficient + '\'' +
                ", PowerCoefficient='" + PowerCoefficient + '\'' +
                ", IPAddress='" + IPAddress + '\'' +
                ", NetAddress='" + getNetAddress() + '\'' +
                ", IedName='" + IedName + '\'' +
                '}';
    }
}
