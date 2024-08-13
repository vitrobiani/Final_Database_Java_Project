import java.util.Set;

public class OrderThroughWebsite extends Order{
    DataBase db = DataBase.getInstance();
    ShippingType shippingType;
    ShippingCompany company;
    public OrderThroughWebsite(Customer customer, int quantity, Product product, ShippingType shippingType) {
        super(customer, quantity, product);
        this.shippingType = shippingType;
        product.updateStock(product.getStock() - quantity);
        this.company = setShippingCompany();
    }

    public ShippingCompany setShippingCompany(){
        Set<ShippingCompany> set = db.getAllShippingCompanies();
        switch(shippingType){
            case EXPRESS: {
                return bestExpressShipping(set);
            }
            case STANDARD:{
                return bestStandardShipping(set);
            }
        }
        return null;
    }

    public ShippingCompany bestExpressShipping(Set<ShippingCompany> set){
        double bestPrice = Double.POSITIVE_INFINITY;
        ShippingCompany bestCompany = null;
        for (ShippingCompany sc: set){
            if (sc.calculateExpressShippingCost(this) < bestPrice){
                bestCompany = sc;
                bestPrice = sc.calculateExpressShippingCost(this);
            }
        }
        return bestCompany;
    }

    public ShippingCompany bestStandardShipping(Set<ShippingCompany> set) {
        double bestPrice = Double.POSITIVE_INFINITY;
        ShippingCompany bestCompany = null;
        for (ShippingCompany sc: set){
            if (sc.calculateRegularShippingCost(this) < bestPrice){
                bestCompany = sc;
                bestPrice = sc.calculateRegularShippingCost(this);
            }
        }
        return bestCompany;
    }

    public String orderDetails(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.orderDetails());
        sb.append("  Shipping Fees: " );
        if (shippingType == ShippingType.STANDARD)
            sb.append(company.calculateRegularShippingCost(this));
        else
            sb.append(company.calculateExpressShippingCost(this));
        sb.append("\nShipping Company: " + company.Name);

        return sb.toString();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString() + "Shipping Fees: " );
        if (shippingType == ShippingType.STANDARD)
            sb.append(company.calculateRegularShippingCost(this));
        else
            sb.append(company.calculateExpressShippingCost(this));

        return sb.toString();
    }
}
