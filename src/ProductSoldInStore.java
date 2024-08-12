public class ProductSoldInStore extends ProductSoldInCountry {
    public ProductSoldInStore(String code, String name, double buyPrice, double sellPrice, int weight, int stock) {
        super(code, name, buyPrice, sellPrice, weight, stock);
    }

    public String toString(){
        return "Where it sells: Store\n " + super.toString();
    }
}
