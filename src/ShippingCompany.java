import java.util.HashMap;

public abstract class ShippingCompany {
    public abstract double calculateExpressShippingCost(Order order);
    public abstract double calculateRegularShippingCost(Order order);
    
}
