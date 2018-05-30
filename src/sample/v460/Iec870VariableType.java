package sample.v460;

public class Iec870VariableType extends ResourceBean {


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

    @Override
    public String toString() {
        return "Iec870VariableType{" +
                "signal='" + getSignalName() + '\'' +
                ", Matrix='" + getMatrix() + '\'' +
                ", ioa1='" + getIec870_ioa1() + '\'' +
                ", resLabel='" + getRecourcesLabel() + '\'' +
                ", symAddr='" + getSymbAddr() + '\'' +
                '}';
    }


    public void accept(VisitorProtocol visitorProtocol){
        visitorProtocol.visit(this);
    }
}
