package sample.v460;

import com.opencsv.bean.CsvBindByPosition;

public class ResourceBean implements Cloneable{

    @CsvBindByPosition(position = 2)
    String driverType;
    @CsvBindByPosition(position = 4)
    String typeName;
    @CsvBindByPosition(position = 5)
    String matrix;
    @CsvBindByPosition(position = 6, required = true)
    String tagname;
    @CsvBindByPosition(position = 12)
    String recourcesLabel;
    @CsvBindByPosition(position = 13)
    String netAddr;
    @CsvBindByPosition(position = 19)
    String symbAddr;
    @CsvBindByPosition(position = 76)
    String iec870_type;
    @CsvBindByPosition(position = 77)
    String iec870_coa1;
    @CsvBindByPosition(position = 78)
    String iec870_ioa1;

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

    public String getIec870_type() {
        return iec870_type;
    }
    public void setIec870_type(String iec870_type) {
        this.iec870_type = iec870_type;
    }

    public String getIec870_coa1() {
        return iec870_coa1;
    }
    public void setIec870_coa1(String iec870_coa1) {
        this.iec870_coa1 = iec870_coa1;
    }

    public String getIec870_ioa1() {
        return iec870_ioa1;
    }
    public void setIec870_ioa1(String iec870_ioa1) {
        this.iec870_ioa1 = iec870_ioa1;
    }

    public String getAlarmClass(){
        switch (getAlarmTypeByRules(getMatrix())){
            case BM:    return "ОС";
            case GM1:   return "АС";
            case GM2:   return "ПС1";
            case GM3:   return "ПС2";
            default:    return "";
        }
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

    public boolean isAdditionalVariable() {
        switch (driverType()){
            case MATHDR32: case Intern: case SNMP32:
                return true;
            default:
                return false;
        }
    }
    public boolean isIec870Variable() {
        switch (driverType()){
            case SPRECON870: case IEC870:
                return true;
            default:
                return false;
        }
    }
    public boolean isIec850Variable() {
        switch (driverType()){
            case IEC850:
                return true;
            default:
                return false;
        }
    }


    private AlarmClassType getAlarmTypeByRules(String srcMatrix){
        if (srcMatrix.contains("_BM") || srcMatrix.contains("_DM") || srcMatrix.startsWith("ОС_")){
            return AlarmClassType.BM;
        }else if(srcMatrix.contains("_GM1") || srcMatrix.startsWith("АС_")){
            return AlarmClassType.GM1;
        }else if(srcMatrix.contains("_GM2") || srcMatrix.startsWith("ПС1_")){
            return AlarmClassType.GM2;
        }else if(srcMatrix.contains("_GM3") || srcMatrix.startsWith("ПС2_")){
            return AlarmClassType.GM3;
        }else if(srcMatrix.isEmpty()){
            return AlarmClassType.NONE;
        }else{
            return AlarmClassType.BM;
        }
    }


    /*private String formatMatrix(String srcMatrix){

    }*/

    @Override
    public String toString() {
        return "ResourceBean{" +
                "driverType='" + driverType + '\'' +
                ", tagname='" + tagname + '\'' +
                '}';
    }
}
