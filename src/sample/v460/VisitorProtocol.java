package sample.v460;

public interface VisitorProtocol {
    String visit(Iec870VariableType iec870VariableType);
    String visit(Iec850VariableType iec850VariableType);


}
