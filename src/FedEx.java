
public class FedEx extends ShippingCompany {
    DataBase db = DataBase.getInstance();
    @Override
    public double calculateExpressShippingCost(Order order) {
        int pricePer10k = 50;
        double weightIn10k = Math.ceil(order.getProduct().getWeight() / 10.0);
        ProductSoldThroughWebsite product = (ProductSoldThroughWebsite)order.getProduct();
        String destCountry = product.getDestCountry();
        int importTax = (int) db.getCountries().get(destCountry);
        return weightIn10k * pricePer10k + importTax;
    }

    @Override
    public double calculateRegularShippingCost(Order order) {
        int pricePer10k = 10;
        double weightIn10k = Math.ceil(order.getProduct().getWeight() / 10.0);
        return weightIn10k * pricePer10k;
    }
}
