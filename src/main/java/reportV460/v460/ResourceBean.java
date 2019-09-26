package reportV460.v460;

import com.opencsv.bean.CsvBindByPosition;
import org.apache.commons.lang3.builder.CompareToBuilder;
import reportV460.Helpers.Helpers;
import reportV460.Helpers.LogInfo;
import reportV460.Helpers.Prefs;
import reportV460.Report.Parsers.EnipObject;

import java.util.List;

import static reportV460.v460.DriverType.*;

public class ResourceBean implements Comparable<ResourceBean>{
    @CsvBindByPosition(position = 0)
    String variableName;
    @CsvBindByPosition(position = 2)
    String driverType;
    @CsvBindByPosition(position = 4)
    String typeName;
    @CsvBindByPosition(position = 5)
    String matrix;
    @CsvBindByPosition(position = 6, required = true)
    String tagname;
    @CsvBindByPosition(position = 7)
    String unit;
    @CsvBindByPosition(position = 9)
    String systemModel;
    @CsvBindByPosition(position = 12) //12
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
    String coefficientTransform;
    AlarmClassType alarmClassType;
    String statusText;
    String signRV;


    public void setCorrectDriverTypeAfterInitAllFields(){
        switch(driverType){
            case "SPRECON870":
                driverType = SPRECON870.name();
                break;
            case "IEC870":
                driverType = isSpreconDriver() ? SPRECON870.name() : IEC870.name();
                break;
            case "IEC850":
                driverType = isSpreconDriver() ? SPRECON850.name() : IEC850.name();
                break;
            case "SNMP32":
                driverType = SNMP32.name();
                break;
            case "Intern":
                driverType = Intern.name();
                break;
            case "MATHDR32":
                driverType = MATHDR32.name();
                break;
            default:
                driverType = UNKNOWN.name();
                break;
        }
    }

    public DriverType getDriverType() {
        return DriverType.valueOf(driverType);
    }

    public String getTagname() {
        return tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSignRV() {return signRV;}



    public String getSystemModel() {
        return systemModel;
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
        setAlarmClassEnum(getAlarmTypeByRules(getMatrix()));
        setStatusText(getMatrix());
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

    public Enum getAlarmClassEnum(){
        return alarmClassType;
    }
    private void setAlarmClassEnum(AlarmClassType alarmClassEnum){
        this.alarmClassType = alarmClassEnum;
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

    public String getVariableName() {return variableName;}
    public String getStatusText() {
        return statusText;
    }
    private void setStatusText(String statusText){
        this.statusText = getStatusTextByRules(statusText).replace("_", "/");
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
    public String getPrefixConnection(){return getTagname().substring(0,51).trim(); }
    public String getPrefixSpreconSymbAddress(){return getSpreconSymbPrefix(getSymbAddr());}
    public String getShortSymbAddress(){return getFormattedIec850Address(getSymbAddr());}
    //public String getIpAddress(){return String.format("10.47.171.%s", getNetAddr());}
    public String getCoefficientTransform(){
        if (coefficientTransform == null || coefficientTransform.isEmpty()){ setDefaultCoefficientTransform(); }
        return coefficientTransform;
    }

    private boolean isVariableU(){
        String lowSymbols = getSignalName().toLowerCase();
        return lowSymbols.contains("напряж") && (getUnit().equals("кВ"));
    }
    private boolean isVariableI(){
        String lowSymbols = getSignalName().toLowerCase();
        return lowSymbols.contains("ток") && (getUnit().equals("А"));
    }

    public void setManualParameter() {
        if(isVariablePositionSprAndManual()){this.signRV = "Ручной ввод";}
    }

    public boolean isVariablePositionSpr(){
        return  !isVariableTI() &&
                (getVariableName().endsWith(".Position") &&
                        ((getDriverType() == DriverType.SPRECON850) ||
                        (getDriverType() == DriverType.SPRECON870)));
    }

    public boolean isVariableEkra(){
        return Helpers.isSubstringFoundWithRegex(getSymbAddr(), "(IED\\d+LD)");
    }

    public boolean isVariablePositionSprAndManual(){
        return  isVariablePositionSpr() && isNotPhysicalPosition();
    }

    private boolean isNotPhysicalPosition(){
        return !getSystemModel().contains("ManualMode.Physical");
    }

    private String setDefaultCoefficient() {
        if (isVariableU() && (isNotTISSNorSPT())) {
            if (Helpers.tryParseInt(getVoltageClass())) {
                String num = Helpers.getTextWithPattern(getVoltageClass(), "(\\d+)");
                return String.format("%s0/1", Integer.parseInt(num));
            }
            return "";
        }
        //if (isVariableI()) { return "2000/1"; }
        return "";
    }

    private boolean isNotTISSNorSPT(){
        return !(getVoltageClass().contains("0,4") || getVoltageClass().contains("0,2"));
    }

    public void setDefaultCoefficientTransform(){
        setCoefficientTransform(setDefaultCoefficient());
    }

    private void setCoefficientTransform(String coefficientTransform) {
        this.coefficientTransform = coefficientTransform;
    }

    public void setCoefficientTransformWithEnips(List<EnipObject> enipObjects){
        for (EnipObject enipObject : enipObjects) {
            if (isEnipObjectCorrect(enipObject)) {
                //LogInfo.setLogData("Энип с  адресом " + enipObject.getIPAddress() + " найден в базе V460");
                if (this.isVariableU()) {
                    this.setCoefficientTransform(enipObject.getVoltageCoefficient() + "/1");
                    break;
                } else if (this.isVariableI()){
                    try {
                        int coef = Integer.parseInt(enipObject.getCurrentCoefficient());
                        if (coef < 400) {
                            this.setCoefficientTransform(String.format("%s/5", coef * 5));
                        } else {
                            this.setCoefficientTransform(String.format("%s/1", coef));
                        }
                        break;
                    } catch (NumberFormatException e) {
                        LogInfo.setErrorData(this.getSignalName() +"\t" + e.getMessage());
                    }
                } else {
                    break;
                }
            }/*else {
                LogInfo.setErrorData("Энип с адресом " + enipObject.getIPAddress() + "не найден в базе V460");
            }*/

        }
    }

    public boolean isVariableManualAttribute(){
        return  getSymbAddr().contains("subVal") ||
                getSymbAddr().contains("subEna") ||
                getSymbAddr().contains("subQ");
    }

    private boolean isEnipObjectCorrect(EnipObject enipObject){
        String enipIp = Prefs.getPrefValue("IP") + this.getNetAddr();
        return enipIp.equals(enipObject.getIPAddress());
    }

    public boolean isAdditionalVariable() {
        return getDriverType().equals(MATHDR32) || getDriverType().equals(Intern) || getDriverType().equals(SNMP32);
    }
    private boolean isIec870Variable() {
        return getDriverType().equals(SPRECON870) || getDriverType().equals(IEC870);
    }
    private boolean isIec850Variable() {
        return getDriverType().equals(SPRECON850) || getDriverType().equals(IEC850);
    }
    public boolean isIecVariable(){return this.isIec870Variable() || this.isIec850Variable();}
    public boolean isVariableTI(){
        switch (this.getDriverType()){
            case SPRECON870: case IEC870:
                return  this.getIec870_type().contains("36") ||
                        this.getIec870_type().contains("34");
            case SPRECON850: case IEC850:
                return this.getSymbAddr().contains("mag");
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
    private String getStatusTextByRules(String srcMatrix) {
        if(Helpers.isMatrixStartsAlarmClass(srcMatrix)){
            return Helpers.getTextWithPattern(srcMatrix, "_(\\w+)/");
        }else if(Helpers.isMatrixEndsWithAlarmClass(srcMatrix)){
            String text = Helpers.getTextWithPattern(srcMatrix, "(^\\w+/\\w+)_");
            if(text.equals(""))
                return Helpers.getTextWithPattern(srcMatrix, "(^\\w+)/");
            return text;
        }else{
            return srcMatrix;
        }
    }
    public void setCorrectMatrixToVariables(){
        if (getSymbAddr().contains("CALH1/Health") ||
                (getSymbAddr().contains("LLN0/Health")) ||
                (getSymbAddr().contains("GGIO1/Ind175")) ||
                (getSignalName().contains("Состояние связи"))){
            setMatrix("ПС2_Неисправность_норма/1_0");
        }
    }
    private String getFormattedIec850Address(String symAddress){
        return Helpers.getTextWithPattern(symAddress, "!(\\w.*)")
                .replace("[ST]","")
                .replace("[MX]","");
    }

    private String getSpreconSymbPrefix(String symAddress){
        return Helpers.getTextWithPattern(symAddress, "(^\\w.*)!");
    }

    private boolean isSpreconDriver(){
        String findHex = Helpers.getTextWithPattern(
                getRecourcesLabel(),
                "(\\w{2}\\.\\w{2}\\.\\w{2}\\.\\w{2})");
        return !findHex.isEmpty();

    }

    private Long getResourceAddressHex(){
        String findHex = Helpers.getTextWithPattern(
                getRecourcesLabel(),
                "(\\w{2}\\.\\w{2}\\.\\w{2}\\.\\w{2})").replace(".","");
        if(!findHex.isEmpty()){ return Long.valueOf(findHex, 16); }
        return 0L;
    }

    private Integer getResourceAddressEkra(){
        String findInt = Helpers.getTextWithPattern(getRecourcesLabel(), "(\\d+)");
        if(!findInt.isEmpty()){ return Integer.parseInt(findInt); }
        return 0;
    }

    @Override
    public String toString() {
        return "ResourceBean{" +
                "driverType='" + driverType + '\'' +
                ", tagname='" + tagname + '\'' +
                '}';
    }

    @Override
    public int compareTo(ResourceBean o) {
        switch (getDriverType()){
            case SPRECON850: case SPRECON870:
                return this.getResourceAddressHex().compareTo(o.getResourceAddressHex());
            case IEC850:
                if (this.getResourceAddressEkra() == 0)
                    return this.getSymbAddr().compareTo(o.getSymbAddr());
                else
                    return this.getResourceAddressEkra().compareTo(o.getResourceAddressEkra());
            case IEC870:
                return new CompareToBuilder()
                        .append(this.getDevice(), o.getDevice())
                        .append(Integer.parseInt(this.getIec870_coa1()), Integer.parseInt(o.getIec870_coa1()))
                        .append(Integer.parseInt(this.getIec870_ioa1()), Integer.parseInt(o.getIec870_ioa1()))
                        .toComparison();
            default:
                return 1;
        }

    }

    public ResourceBean(ResourceBean bean){
        this(
                bean.getDriverType().name(),
                bean.getTypeName(),
                bean.getMatrix(),
                bean.getTagname(),
                bean.getRecourcesLabel(),
                bean.getNetAddr(),
                bean.getSymbAddr(),
                bean.getIec870_type(),
                bean.getIec870_coa1(),
                bean.getIec870_ioa1()
        );
    }

    public ResourceBean(){
        this(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }

    public ResourceBean(
            String driverType,
            String typeName,
            String matrix,
            String tagname,
            String recourcesLabel,
            String netAddr,
            String symbAddr,
            String iec870_type,
            String iec870_coa1,
            String iec870_ioa1
    ) {
        this.driverType = driverType;
        this.typeName = typeName;
        this.matrix = matrix;
        this.tagname = tagname;
        this.recourcesLabel = recourcesLabel;
        this.netAddr = netAddr;
        this.symbAddr = symbAddr;
        this.iec870_type = iec870_type;
        this.iec870_coa1 = iec870_coa1;
        this.iec870_ioa1 = iec870_ioa1;

    }

}
