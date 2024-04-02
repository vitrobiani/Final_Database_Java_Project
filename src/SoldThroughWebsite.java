public class SoldThroughWebsite extends Product {
    public static final double dollarRate = 4.0;
    public String destCountry;
    ShippingType type;
    public SoldThroughWebsite(String name, double buyPrice, double sellPrice, int weight, String destCountry, ShippingType type) {
        super(name, buyPrice, sellPrice, weight);
        this.destCountry = destCountry;
        this.type = type;
    }
}
