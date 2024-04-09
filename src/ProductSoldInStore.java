public class ProductSoldInStore extends ProductSoldInCountry {
    public ProductSoldInStore(String code, String name, double buyPrice, double sellPrice, int weight) {
        super(code, name, buyPrice, sellPrice, weight);
    }

    public String toString(){
        return "Where it sells: Store\n " + super.toString();
    }
}
