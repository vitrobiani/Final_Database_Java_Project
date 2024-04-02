public class DHL extends ShippingCompany{
    final static int baseDeliveryPrice = 100;
    @Override
    public double calculateExpressShippingCost(Order order) {

        return 0;
    }

    @Override
    public double calculateRegularShippingCost(Order order) {
        return 0;
    }
}
