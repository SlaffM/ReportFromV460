package sample.Report;

public class ReportData {

    private String title;
    private String panelLocation;   //Расположение
    private String panelTitle;      //Наименование шкафа
    private String connection;      //Наименование присоединения
    private String controllerTitle; //Обозначение контроллера
    private String ipAddress;       //Ip адрес

    private String system;          //Система
    private String voltageClass;    //Класс напряжения
    private String device;          //Устройство
    private String signalName;      //Наименование сигнала
    private String status;          //Текст состояния
    private String alarmClass;      //Класс тревог
    private String iec870type;      //Тип АСДУ
    private String iec870coa;       //Адрес АСДУ
    private String iec870ioa;       //Адрес сигнала
    private String resourceLabel;   //Поле RecourcesLabel
    private String symbAddr;        //Поле Symbolic address


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPanelLocation() {
        return panelLocation;
    }

    public void setPanelLocation(String panelLocation) {
        this.panelLocation = panelLocation;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getControllerTitle() {
        return controllerTitle;
    }

    public void setControllerTitle(String controllerTitle) {
        this.controllerTitle = controllerTitle;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVoltageClass() {
        return voltageClass;
    }

    public void setVoltageClass(String voltageClass) {
        this.voltageClass = voltageClass;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlarmClass() {
        return alarmClass;
    }

    public void setAlarmClass(String alarmClass) {
        this.alarmClass = alarmClass;
    }

    public String getIec870type() {
        return iec870type;
    }

    public void setIec870type(String iec870type) {
        this.iec870type = iec870type;
    }

    public String getIec870coa() {
        return iec870coa;
    }

    public void setIec870coa(String iec870coa) {
        this.iec870coa = iec870coa;
    }

    public String getIec870ioa() {
        return iec870ioa;
    }

    public void setIec870ioa(String iec870ioa) {
        this.iec870ioa = iec870ioa;
    }

    public String getResourceLabel() {
        return resourceLabel;
    }

    public void setResourceLabel(String resourceLabel) {
        this.resourceLabel = resourceLabel;
    }

    public String getSymbAddr() {
        return symbAddr;
    }

    public void setSymbAddr(String symbAddr) {
        this.symbAddr = symbAddr;
    }


    private String getAlarmClassFromStatus(String status){
        return status;
    }


    private String getStatusWithoutAlarmClass(String status){
        return status;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "signalName='" + signalName + '\'' +
                ", status='" + status + '\'' +
                ", iec870type='" + iec870type + '\'' +
                ", iec870coa='" + iec870coa + '\'' +
                ", iec870ioa='" + iec870ioa + '\'' +
                ", resourceLabel='" + resourceLabel + '\'' +
                ", symbAddr='" + symbAddr + '\'' +
                '}';
    }
}
