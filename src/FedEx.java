public class FedEx extends ShippingCompany {
    @Override
    public double calculateExpressShippingCost(Order order) {
        return 0;
    }

    @Override
    public double calculateRegularShippingCost(Order order) {
        return 0;
    }
}
