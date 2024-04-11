public class OrderCreator implements Creator<Order> {
    @Override
    public Order create(PairSet set) {
        Product product = (Product) set.get("Product");
        String productClass = (String) set.get("ProductClass");
        int quantity = (int) set.get("Quantity");
        Customer customer = (Customer) set.get("Customer");

        if (productClass.equals(ProductSoldInStore.class.getSimpleName()) || productClass.equals(ProductSoldToWholesalers.class.getSimpleName())){
            return new OrderInCountry(customer, quantity, product);
        } else if (productClass.equals(ProductSoldThroughWebsite.class.getSimpleName())) {
            return new OrderThroughWebsite(customer, quantity, product, (ShippingType) set.get("ShippingType"));
        }
        return null;
    }
}
