package sample.v460;

import com.opencsv.bean.CsvBindByPosition;

public class Iec850VariableType implements AbstractBean {

    @CsvBindByPosition(position = 2)
    private String driverType;

    @CsvBindByPosition(position = 4)
    private String typeName;

    @CsvBindByPosition(position = 5)
    private String matrix;

    @CsvBindByPosition(position = 6, required = true)
    private String tagname;

    @CsvBindByPosition(position = 12)
    private String recourcesLabel;

    @CsvBindByPosition(position = 13)
    private String netAddr;

    @CsvBindByPosition(position = 19)
    private String symbAddr;

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public String getRecourcesLabel() {
        return recourcesLabel;
    }

    public void setRecourcesLabel(String recourcesLabel) {
        this.recourcesLabel = recourcesLabel;
    }

    public String getNetAddr() {
        return netAddr;
    }

    public void setNetAddr(String netAddr) {
        this.netAddr = netAddr;
    }

    public String getSymbAddr() {
        return symbAddr;
    }

    public void setSymbAddr(String symbAddr) {
        this.symbAddr = symbAddr;
    }

    public String getPanelLocation(){
        return getTagname().substring(0,8).trim();
    }
    public String getSystem(){
        return getTagname().substring(8,16).trim();
    }
    public String getVoltageClass(){
        return getTagname().substring(16,24).trim();
    }
    public String getConnectionTitle(){
        return getTagname().substring(24,51).trim();
    }
    public String getDevice(){
        return getTagname().substring(51,78).trim();
    }
    public String getSignalName(){
        return getTagname().substring(78).trim();
    }
    public String getPrefixTagname(){
        return getTagname().substring(0,78).trim();
    }

    public String getAlarmClass(){
        return getMatrix();
    }

    @Override
    public String toString() {
        return "Iec870VariableType{" +
                "signal='" + getSignalName() + '\'' +
                ", Matrix='" + getMatrix() + '\'' +
                ", resLabel='" + getRecourcesLabel() + '\'' +
                ", symAddr='" + getSymbAddr() + '\'' +
                '}';
    }

    public void accept(VisitorProtocol visitorProtocol){
        visitorProtocol.visit(this);
    }

    @Override
    public boolean isAdditionalVariable() {
        switch (driverType()){
            case MATHDR32: case Intern: case SNMP32:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isIec870Variable() {
        switch (driverType()){
            case SPRECON870: case IEC870:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isIec850Variable() {
        switch (driverType()){
            case IEC850:
                return true;
            default:
                return false;
        }
    }

    @Override
    public DriverType driverType(){
        switch(getDriverType()){
            case "SPRECON870":
                return DriverType.SPRECON870;
            case "IEC870":
                return DriverType.IEC870;
            case "IEC850":
                return DriverType.IEC850;
            case "SNMP32":
                return DriverType.SNMP32;
            case "Intern":
                return DriverType.Intern;
            case "MATHDR32":
                return DriverType.MATHDR32;
            default:
                return DriverType.UNKNOWN;
        }
    }
}
