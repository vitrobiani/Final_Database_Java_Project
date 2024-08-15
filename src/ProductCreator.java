
public class ProductCreator implements Creator<Product>{
    ProductType type;
    DataBase db = DataBase.getInstance();
    @Override
    public Product create(PairSet set) {
        type = (ProductType) set.get("ProductType");
        String code = (String) set.get("code");
        String name = (String) set.get("name");
        double buyPrice = (double) set.get("buyPrice");
        double sellPrice = (double) set.get("sellPrice");
        int weight = (int) set.get("weight");
        int stock = (int) set.get("stock");
        switch (type) {
            case SOLD_THROUGH_WEBSITE:
                return new ProductSoldThroughWebsite(code, name, buyPrice, sellPrice, weight, stock,(Country) set.get("srcCountry"), (ShippingType) set.get("ShippingType"));
            case SOLD_IN_STORE:
                return new ProductSoldInStore(code, name, buyPrice, sellPrice, weight, stock);
            case SOLD_TO_WHOLESALERS:
                return new ProductSoldToWholesalers(code, name, buyPrice, sellPrice, weight, stock);
            default:
                System.out.println("No such type");
                return null;
        }
    }
}
