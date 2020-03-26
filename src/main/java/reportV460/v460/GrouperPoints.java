package reportV460.v460;

public enum GrouperPoints
{
    GROUP_BY_NETADDR("по полю NetAddr"),
    GROUP_BY_PANEL("по номеру панели");

    private String name;

    GrouperPoints(String name) {
        this.name = name;
    }

    public String GrouperPoints() {
        return name;
    }
    @Override public String toString() { return name; }

}


