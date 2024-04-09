public abstract class ProductSoldInCountry extends Product {
    public ProductSoldInCountry(String code, String name, double buyPrice, double sellPrice, int weight) {
        super(code, name, buyPrice, sellPrice, weight);
    }

    public String toString(){
        return super.toString();
    }

}
