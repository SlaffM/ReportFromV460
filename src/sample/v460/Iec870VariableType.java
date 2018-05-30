package sample.v460;

public class Iec870VariableType extends ResourceBean {



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
