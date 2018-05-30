package sample.v460;

public interface AbstractBean {

    void accept(VisitorProtocol visitorProtocol);
    DriverType driverType();
    String getPrefixTagname();

    boolean isAdditionalVariable();
    boolean isIec870Variable();
    boolean isIec850Variable();


}
