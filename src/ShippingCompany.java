import java.util.HashMap;

public abstract class ShippingCompany {
    public static HashMap<String,Integer> importTax = new HashMap<>();
    public abstract double calculateExpressShippingCost(Order order);

    public abstract double calculateRegularShippingCost(Order order);
    
    public static void addImportTax(String country, int tax){
        importTax.put(country,tax);
    }
}
