public class ProductSoldThroughWebsite extends Product {
    public static final double dollarRate = 4.0;
    public Country sourceCountry;
    ShippingType type;

    public ProductSoldThroughWebsite(String code, String name, double buyPrice, double sellPrice, int weight, int stock, Country sourceCountry, ShippingType type) {
        super(code, name, buyPrice, sellPrice, weight, stock);
        this.sourceCountry = sourceCountry;
        this.type = type;

    }

    public Country getSourceCountry() {
        return sourceCountry;
    }

    public ShippingType getType(){
        return type;
    }


    public String toString(){
        return "Where it sells: Website " + "\nDestination Country: " + sourceCountry + " , Shipping Types Available: " +
                type.toString() + "\n" + super.toString();
    }
}
