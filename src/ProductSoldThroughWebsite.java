public class ProductSoldThroughWebsite extends Product {
    public static final double dollarRate = 4.0;
    public String destCountry;
    ShippingType type;

    public ProductSoldThroughWebsite(String code, String name, double buyPrice, double sellPrice, int weight, String destCountry, ShippingType type) {
        super(code, name, buyPrice, sellPrice, weight);
        this.destCountry = destCountry;
        this.type = type;

    }

    public String getDestCountry() {
        return destCountry;
    }

    public ShippingType getType(){
        return type;
    }

    public String toString(){
        return "Where it sells: Website " + "\nDestination Country: " + destCountry + " , Shipping Types Available: " +
                type.toString() + "\n" + super.toString();
    }
}
