public class ProductSoldToWholesalers extends ProductSoldInCountry {
    public ProductSoldToWholesalers(String code, String name, double buyPrice, double sellPrice, int weight) {
        super(code, name, buyPrice, sellPrice, weight);
    }

    public String toString(){
        return "Where it sells: Wholesalers\n " + super.toString();
    }
}
