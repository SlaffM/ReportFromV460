package sample.v460;

import com.opencsv.bean.CsvBindByPosition;

public class Iec870Variable extends AbstractBean {

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

    @CsvBindByPosition(position = 76)
    private String iec870_type;

    @CsvBindByPosition(position = 77)
    private String iec870_coa1;

    @CsvBindByPosition(position = 78)
    private String iec870_ioa1;

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
        return "Iec870Variable{" +
                "signal='" + getSignalName() + '\'' +
                ", Matrix='" + getMatrix() + '\'' +
                ", ioa1='" + getIec870_ioa1() + '\'' +
                ", resLabel='" + getRecourcesLabel() + '\'' +
                ", symAddr='" + getSymbAddr() + '\'' +
                '}';
    }
}
