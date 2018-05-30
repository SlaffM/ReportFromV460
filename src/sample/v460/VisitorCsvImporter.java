package sample.v460;

public class VisitorCsvImporter implements VisitorProtocol{

    @Override
    public String visit(Iec870VariableType iec870VariableType) {
        return iec870VariableType.getPrefixTagname();
    }
    @Override
    public String visit(Iec850VariableType iec850VariableType) {
        return iec850VariableType.getPrefixTagname();
    }



}
