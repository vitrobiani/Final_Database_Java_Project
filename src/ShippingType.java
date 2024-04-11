public enum ShippingType {
    STANDARD("Standard Shipping", true),
    EXPRESS("Express Shipping", true),
    ALL_SHIPPING("All Shipping Options", false);

    private final String option;
    public boolean forOrder;

    ShippingType(String option, boolean forOrder){
        this.option = option;
        this.forOrder = forOrder;
    }

    public String toString(){
        return option;
    }
}
