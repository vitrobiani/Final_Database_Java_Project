public enum ShippingType {
    STANDARD("Standard Shipping"),
    EXPRESS("Express Shipping"),
    ALL_SHIPPING("All Shipping Options");

    private String option;
    ShippingType(String option){
        this.option = option;
    }

    public String toString(){
        return option;
    }
}
