package sample.v460;

public class Iec850VariableType extends ResourceBean {

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

}