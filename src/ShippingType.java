public enum ShippingType {
    STANDARD("Standard Shipping", true, "'standard'"),
    EXPRESS("Express Shipping", true, "'express'"),
    ALL_SHIPPING("All Shipping Options", false, "'both'");

    private final String option;
    public boolean forOrder;
    public final String sqlOpt;

    ShippingType(String option, boolean forOrder, String sqlOpt){
        this.option = option;
        this.forOrder = forOrder;
        this.sqlOpt = sqlOpt;
    }

    public String toString(){
        return option;
    }

    public String sqlToString(){
        return sqlOpt;
    }
}
