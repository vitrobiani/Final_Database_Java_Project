public class DHL extends ShippingCompany{
    final static int baseDeliveryPrice = 100;
    DataBase db = DataBase.getInstance();
    @Override
    public double calculateExpressShippingCost(Order order) {
        ProductSoldThroughWebsite product = (ProductSoldThroughWebsite)order.getProduct();
        String destCountry = product.getDestCountry();
        int importTax = (int) db.getCountries().get(destCountry);
        return (baseDeliveryPrice + importTax);
    }

    @Override
    public double calculateRegularShippingCost(Order order) {
        double productPrice = order.getProduct().getSellPrice();
        double productPriceInDollars = productPrice / ProductSoldThroughWebsite.dollarRate;
        double deliveryPrice = productPriceInDollars * 0.1;
        if(deliveryPrice < baseDeliveryPrice){
            return deliveryPrice;
        }
        else return baseDeliveryPrice;
    }
}
