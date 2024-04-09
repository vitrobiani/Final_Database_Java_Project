
public class ProductCreator implements Creator<Product>{
    ProductType type;
    @Override
    public Product create(PairSet set) {
        type = (ProductType) set.get("ProductType");
        String code = (String) set.get("code");
        String name = (String) set.get("name");
        double buyPrice = (double) set.get("buyPrice");
        double sellPrice = (double) set.get("sellPrice");
        int weight = (int) set.get("weight");
        switch (type) {
            case SOLD_THROUGH_WEBSITE:
                return new ProductSoldThroughWebsite(code, name, buyPrice, sellPrice, weight, (String) set.get("destCountry"), (ShippingType) set.get("ShippingType"));
            case SOLD_IN_STORE:
                return new ProductSoldInStore(code, name, buyPrice, sellPrice, weight);
            case SOLD_TO_WHOLESALERS:
                return new ProductSoldToWholesalers(code, name, buyPrice, sellPrice, weight);
            default:
                System.out.println("No such type");
                return null;
        }
    }
}
