package sample.v460;

public class FactoryIecType {

    public AbstractBean createIecVariable(Object object) {
        AbstractBean bean = null;
        if (object instanceof Iec870Variable) {
            bean = new Iec870Variable();
        } else if (object instanceof Iec850Variable) {
            bean = new Iec850Variable();
        }
        return bean;
    }

}
